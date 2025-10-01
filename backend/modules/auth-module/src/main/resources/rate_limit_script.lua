local key = KEYS[1]
local blocked_key = KEYS[2]
local observe_ttl = ARGV[1]
local lock_ttl = ARGV[2]
local max_attempt = ARGV[3]

if redis.call("EXISTS", blocked_key) == 1 then
    return 0
end
local attempt = redis.call("GET", key)
if attempt == nil then
    redis.call("SET", key, 1, "EX", tonumber(observe_ttl))
    return 1
else
    if tonumber(attempt) < tonumber(max_attempt) then
        redis.call("INCR", key)
        return 1
    else
        redis.call("DEL", key)
        redis.call("SET", blocked_key, "blocked", "EX", tonumber(lock_ttl))
        return 0
    end
end