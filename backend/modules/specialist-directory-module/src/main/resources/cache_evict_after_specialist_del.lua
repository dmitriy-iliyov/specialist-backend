local shrt_info = KEYS[1]
local spec = KEYS[2]
local crtd_count_t = KEYS[3]
local crtd_count_f = KEYS[4]
redis.call("DEL", shrt_info)
redis.call("DEL", spec)
redis.call("DEL", crtd_count_f)
local v = tonumber(redis.call("HGET", crtd_count_t, "value") or "0")
if v > 0 then
    redis.call("HINCRBY", crtd_count_t, "value", -1)
end
return 1