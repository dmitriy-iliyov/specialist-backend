local crtd_count_t = KEYS[1]
local crtd_count_f = KEYS[2]
local v = tonumber(redis.call("HGET", crtd_count_t, "value") or "0")
if v > 0 then
    redis.call("HINCRBY", crtd_count_t, "value", -1)
end
redis.call("DEL", crtd_count_f)
return 1