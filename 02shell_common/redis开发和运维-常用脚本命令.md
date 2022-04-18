# redis常见操作

本文档用的图床地址：

https://imgkr.com/#upload



## redis启动停止脚本

### 1、启动redis

> 有三种方法启动redis：默认配置、运行配置、配置文件启动

#### 1.1 默认配置方式启动

> 一般推荐自己学习的时候使用，随用随启

```shell
redis-server
```

####  1.2 运行配置方式启动

> 在上面是启动基础上面，手动指定部分配置项

```shell
redis-server --configKey1 configValue1 --configKey2 configValue2
# 例如 指定端口
redis-server --port 6380
```

#### 1.3 配置文件方式启动

> 生产环境推荐这种方式启动。

```
redis-server /opt/redis/redis.conf
```

### 2、Redis命令行客户端

#### 第一种：交互方式

方式：

```shell
redis-cli -h {host} -p {port}
```

通过上面的方式，进入交互模式，后面不需要在执行redis-cli的方式

样例：

```shell
redis-cli -h 127.0.0.1 -p 6379
```

![](https://static01.imgkr.com/temp/e75b1859314f4f409272b9ae3b49ed24.png)



#### 第二种：命令方式

```shell
redis-cli -h {host} -p {port} {command}
```


样例：

```shell
redis-cli -h 127.0.0.1 -p 6379 get hello
```

![](https://static01.imgkr.com/temp/b98a9406bb904d589ba73adfb4709990.jpg)

这里有两点要注意：
1）如果没有-h参数，那么默认连接127.0.0.1；如果没有-p，那么默认6379端口，也就是说如果-h和-p都没写就是连接127.0.0.1：6379这个Redis实例。
2）redis-cli是学习Redis的重要工具，后面的很多章节都是用它做讲解，同时redis-cli还提供了很多有价值的参数，可
以帮助解决很多问题。

### 3、停止redis服务

```shell
# 停止127.0.0.1 的6379端口的redis服务
redis-cli shutdown 

# 关闭的时候 选择是否生成持久化文件
redis-cli shutdown nosave|save

```

## redis常用脚本

### 脚本命令的时间复杂度

#### 字符串类型命令时间复杂度
![](https://static01.imgkr.com/temp/9b934a37ad8a40d6be9d4ef3c5a5e30a.png)

#### 哈希类型命令时间复杂度
![](https://static01.imgkr.com/temp/8aa1df548f8f45109e6e8f04f7279624.jpg)

#### 列表类型命令时间复杂度
![](https://static01.imgkr.com/temp/72cfb399a24f4a08a99b28d6e0fbf600.png)

#### 集合类型命令时间复杂度

![](https://static01.imgkr.com/temp/9f725adb15fe4521b7cc4c309ba35250.png)

#### 有序集合类型命令时间复杂度

![](https://static01.imgkr.com/temp/6ffa14789a8b4b16b26268c6eb90bbcb.png)



### 全局命令

#### 1 查看所有键 keys *



### 常用命令（字符串）

#### 1 设置值set

```shell
set key value [ex seconds] [px milliseconds] [nx|xx]
# 参数说明
# key 存储的 键
# value 存储的 值
# ex seconds: 为键设置秒级过期时间
# px milliseconds: 为键设置毫秒级过期时间
# nx: 键必须不存在，才可以设置成功，用于添加
# xx: 与nx相反，键必须存在，才可以设置成功，用于更新。


# setex和setnx 两个命令 和上面的set指定的参数一致，如下：
setex key seconds value
setnx key value

# 返回值说明
# 成功返回1  否则返回0
```

#### 2 获取值get

```shell
get key

# 如果要获取的键不存在，则返回nil（空）
```

#### 3 批量设置值 mset

```shell
mset key value [key value ...]
```

#### 4 批量获取值mget

```shell
mget key [key ...]
```

#### 5 计数 incr

```shell
incr key
# 返回结果说明：
# 1 值不是整数,返回错误
# 2 值是整数，返回自增后的结果
# 3 键不存在，按照值为0自增，返回结果为1
```

除了`incr`命令，redis还提供了的`decr`（自减）、`incrby`（自增指定数字）、`decrby`（自减指定数字）、`incrbyfloat`（自增浮点数）:

```shell
decr key
incrby key increment
decrby key decrement
incrbyfloat key increment
```

### 不常用命令（字符串）

#### 1 追加值 append

```shell
append key value
# 可以向字符串尾部追加值
```

#### 2 字符串长度strlen

```shell
strlen key
# PS 每个中文占用3个字节。
# 例如 "中国" strlen 返回的是6
```

#### 3 设置并返回原值 getset

```shell
getset key value
# 返回原来的键值
```

#### 4 设置指定位置的字符 setrange

```shell
setrange key offset value
# set redis pest 
# setrange
```

#### 5 获取部分字符串 getrange

```shell
getrange key start end

# 例如 redis 的value值为 best
# getrange redis 0 1
# "be"
```



### 哈希命令

#### 1 设置值 hset

```shell
hset key field value 
# 例子
# hset user:1 name tom
# 返回值说明：成功 返回 1，否则 返回0
# 此外还提供了 hsetnx命令，和 setnx的一样方式 （nx: 键必须不存在，才可以设置成功，用于添加）
```

#### 2 获取值 hget

```shell
hget key field
# 例子
# hget user:1 name
# 如果键或者field不存在，会返回nil

```

#### 3 删除field 

```shell
hdel key field [field ...]
# hdel 会删除一个或多个field，返回结果为成功删除的field的个数
# 没有数据删除 返回 0
```

#### 4 计算field个数 hlen

```shell
hlen key
```

#### 5 批量设置或获取field-value（hmget, hmset）

```shell
hmget key field [field ...]
hmset key field value [field value ...]
```

#### 6 判断field是否存在（hexists）

```shell
hexists key field
# 存在 返回1  否则 返回0
```

#### 7 获取所有field（hkeys）

```shell
hkeys key 
# hkeys 命令应该叫 hfields 更合适，他返回指定哈希键所有的field
```

#### 8 获取所有value（hvals）

```shell
hvals key
```

#### 9 获取所有的field-value（hgetall）

```shell
hgetall key
```

#### 10 hincrby  hincrbyfloat

```shell
hincrby key field
hincrbyfloat key field
# hincrby和hincrbyfloat，就像incrby和incrbyfloat命令一样，但是它们的作用域是field。
```

#### 11 计算value的字符串长度（需要redis3.2以上）

```shell
hstrlen key field
```

### 列表命令

#### 一 添加操作

##### 1 从右边插入元素

```shell
rpush key value [value ...]
```

##### 2 从左边插入元素

```shell
lpush key value [value ...]
```

##### 3 向某个元素前或者后插入元素

```shell
linsert key before|after pivot value
# linsert 命令会从列表中找到等于pivot的元素，在其前（before）或者后（after）插入一个新的元素value，例如：
# linsert listkey before b java
# (integer) 4
# 返回结果 4 代表当前命令（列表）的长度，当前列表变为：
# lrange listkey 0 -1
# "c"
# "java"
# "b"
# "a"
```

#### 二 查找

##### 1 获取指定范围内的元素列表lrange

```shell
lrange key start end
# start 和 end的索引的下标，他们的特点如下：
# 第一：索引下标从左到右分别是0到N-1，但是从右到左分别是-1到-N。
# 第二：lrange中的end包含了自身
# 例如，想获取列表的第2到第4个元素：
# lrange listkey 1 3
```

##### 2 获取列表指定索引下标的元素 lindex

```shell
lindex key index
# 注意 最后一位的话 是 -1
```



##### 3 获取列表长度 llen

```shell
llen key
```



#### 三 删除

##### 1 从列表左侧弹出元素 lpop

```shell
lpop key
```



##### 2 从列表右侧弹出 rpop

```shell
rpop key
```



##### 3 删除指定元素 lrem

```shell
lrem key count value
# lrem命令会从列表中找到等于value的元素进行删除，根据count不同分为三种情况：
# count > 0，从左到右，删除最多count个元素。
# count < 0，从右到左，删除最多count绝对值个元素
# count = 0，删除所有
# 例如：当前列表为："a a a a a java b a"
# 执行命令：lrem listkey 4 a
# 命令的含义：从左到右，删除最多4个 value = a的元素
# 结果为："a java b a"
```



##### 4 按照索引范围修剪列表 ltrim

```shell
ltrim key start end
# 样例列表数据：a java b a
# 例如：ltrim listkey 1 3  -- 截取第2到第4个元素
# 结果为：java b a
```

#### 四 修改

##### 1 修改指定索引下标的元素 lset

```shell
lset key index newValue
# 指定位置 设置为 新的值
```

#### 五 阻塞操作

##### 1 阻塞弹出

```shell
blpop key [key ...] timeout
brpop key [key ...] timeout
# timeout单位是 秒
# 1）列表为空：如果timeout=3，那么客户端要等到3秒返回，如果timeout=0，那么客户端一直阻塞等下去
# 2）列表不为空：客户端会立即返回

# 注意点
# 第一：如果是多个键，那么会从左到右遍历键，一旦有一个键能弹出元素，客户端立即返回
# 第二：如果多个客户端对同一个键执行 blpop|brpop，那么最先执行blpop|brpop命令的客户端可以获取到弹出的值。
```



### 集合命令

#### 1 添加元素 sadd

```shell
sadd key element [element ...]
# 返回结果 为添加成功的元素个数
```

#### 2 删除元素 srem

```shell
srem key element [element ...]
# 返回结果 为删除成功的元素个数
```



#### 3 计算元素个数 scard

```shell
scard key
# scard的时间复杂度为O(1)，它不会遍历集合所有元素，而是直接用redis内部的变量。
```

#### 4 判断元素是否在集合中 sismember

```shell
sismember key element
# 如果给定元素element在集合内，返回1，否则 返回0
```

#### 5 随机从集合中返回指定个数元素 srandmember

```shell
srandmember key [count]
# [count]是可选参数，如果不写，默认为1 （随机返回元素，元素不会被删除）
```

#### 6 从集合中随机弹出元素 spop

```shell
spop key
#spop 操作可以从集合中弹出一个元素 （随机返回元素，元素会被删除）
# 注意：从redis3.2版本开始，spop也支持[count]参数
```

#### 7 获取所有元素 smembers

```shell
smembers key
# 返回所有元素，结果无序
# smembers和lrange、hgetall都属于比较重的命令，如果元素过多存在阻塞redis的可能性，这时候需要用sscan来完成。
```

#### 8 求多个集合的交集 sinter

```shell
sinter key [key ...]
```

8 求多个集合的并集 sunion

```sql
sunion key [key ...]
```

#### 9 求多个集合的差集 sdiff

```sql
sdiff key [key ...]
```

#### 10 将交集、并集、差集的结果保存

```shell
sinterstore destination key [key ...]
sunionstore destination key [key ...]
sdiffstore destination key [key ...]
```

### 有序集合常用命令

#### 1 添加成员 zadd

```shell
zadd key score member [score member ...]
# 返回值 是添加成功的个数
# redis3.2 为zadd添加了nx、xx、ch、incr四个选型：
# nx：member必须不存在，才可以设置成功，用于添加。
# xx：member必须存在，才可以设置成功，用于更新。
# ch：返回此次操作后，有序集合元素和分数发生变化的个数
# incr：对score做增加，相当于后面介绍的zincrby
#
# 有序集合相比集合提供了排序字段，但是也产生了代价，zadd的时间复杂度为O(log(n))，sadd的时间复杂度为O(1)
```

#### 2 计算成员个数 zcard

```shell
zcard key
# 和集合类型的scard一样，zcard的时间复杂度为O(1)
```

#### 3 计算某个成员的分数 zscore

```shell
zscore key member
# 成员不存在，返回nil
```

#### 4 计算成员排名 zrank|zrevrank

```shell
# 分数从低到高返回排名
zrank key member
# 分数 从高到低 返回排名
zrevrank key member
# 排名从0开始计算
```

#### 5 删除成员 zrem

```shell
zrem key member [member ...]
# 返回结果是成功删除的个数
```

#### 6 增加成员的分数 zincrby

```shell
zincrby key increment member
```

#### 7 返回指定排名范围的成员 zrange|zrevrange

```shell
# 从低到高返回
zrange    key start end [withscores]
# 反之，从高到低返回
zrevrange key start end [withscores]
# 如果加上 withscores，同时返回成员的分数
```

#### 8 返回指定分数范围的成员 zrangebyscore| zrevrangebyscore

```shell
# 从低到高
zrangebyscore    key min max [withscores] [limit offset count]
# 从高到低
zrevrangebyscore key min max [withscores] [limit offset count]
# withscores选项会同时返回每个成员的分数。
# [limit offset count] 选项可以限制输出的起始位置和个数

# 同时min和max还支持开区间（小括号） 和闭区间（中括号） ， -inf和+inf分别代表无限小和无限大
```

#### 9 返回指定分数范围成员个数 zcount

```shell
zcount key min max
```

#### 10 删除指定排名内的升序元素 zremrangebyrank

```shell
zremrangebyrank key start end
# zremrangebyrank user:ranking 0 2
# 返回值：3
```

#### 11 删除指定分数范围的成员 zremrangebyscore

```shell
zremrangebyscore key min max
# 返回结果是成功删除的个数
# zremrangebyscore user:ranking (250 +inf
# 删除超过250分以上的成员
```

#### 12 交集 zinterstore

```sql
zinterstore destination numkeys key [key ...] [weights weight [weight ...]] [aggregate sum|min|max]
# destination：交集计算结果保存到这个键。
# numkeys：需要做交集计算键的个数。
# key [key ...]：需要做交集计算的键。
# weights weight [weight ...]：每个键的权重，在做交集计算时，每个键中的每个member会将自己分数乘以这个权重，每个键的权重默认是1.
#　aggregate sum|min|max：计算成员交集后，分别可以按照sum(和)、min(最小值)、max(最大值)做汇总，默认值是sum。

# 样例
# zinterstore user:ranking:1_inter_2 2 user:ranking:1 user:ranking:2
# zinterstore user:ranking:1_inter_2 2 user:ranking:1 user:ranking:2 weights 1 0.5 aggregate max
```

#### 13 并集 zunionstore

```shell
zinterstore destination numkeys key [key ...] [weights weight [weight ...]] [aggregate sum|min|max]
# 具体的参数可以参考 交集
```



### 键管理-单个键

#### 1 键重命名 rename

```sql
rename key newkey
# renamenx 命令，确保只有 newkey不存在时候 才能被覆盖
# 返回OK 是重命名成功
# 返回结果为0 表示没有完成重命名

```

#### 2 随机返回一个键 randomkey

```sql
randomkey
```

#### 3 键过期（expire）

```sql
expire key seconds # 键在seconds秒后过期
expireat key timestamp # 键在秒级时间戳timestamp后过期

ttl key
pttl key
# ttl命令 和 pttl 都可以查询键的剩余过期时间，pttl精度-更高，可以达到毫秒级别
# 他们有3各种返回值
# 大于等于0的整数：键剩余的过期时间（ttl是秒，pttl是毫秒）
# -1：键没有设置过期时间
# -2：键不存在

# 除此之外，redis2.6版本后提供了毫秒级的过期方案：
pexpire key milliseconds # 键在millionseconds毫秒后过期
pexpireat key millionseconds-timestamp #键在毫秒级时间戳timestamp后过期


```

在使用redis相关过期命令时，需要注意以下几点：

1. 如果expire key的键不存在，返回结果为0；
2. 如果过期时间为负值，键会立即被删除，犹如使用del命令一样；
3. persist命令可以将键的过期时间清除；
4. 对于字符串类型键，执行set命令会去掉过期时间，这个问题很容易在开发中被忽视；
5. redis不支持二级数据结构（例如哈希、列表）内部元素的过期功能，例如不能对列表类型的一个元素做过期时间设置。
6. setex命令作为set+expire的组合，不但是原子执行，同时减少了一次网络通讯的时间

#### 4 迁移键(move、dump+restore、migrate)

```sql
#1. move 
move key db #将指定的key迁移到目标db，不建议生产用

#2. dump+restore
dump key  # 在源redis上，将键值序列化，格式采用的是RDB格式
restore key ttl value # 在目标redis上，restore命令将上面序列化的值进行复原，其中ttl代表过期时间，如果ttl=0表示不过期

#3. migrate
migrate host port key|"" destination-db timeout [copy] [replace] [keys key [key ...]]
# migrate 将 dump、restore、del三个命令组合。
# migrate命令具有原子性，且从3.0.6 版本开始 支持多个键
# 参数说明：
# host：目标redis的IP地址
# port：目标redis的端口
# key|""：在redis3.0.6之前，migrate只支持迁移一个键；但是3.0.6之后版本，支持多个键，如果要迁移多个键，此处为空字符串""
# destination-db：目标redis的数据库索引，例如要迁移到0号数据库，这里就写0
# timeout：迁移的超时时间（单位 毫秒）
# [copy]：如果添加此选项，迁移后不删除源键
# [replace]：如果添加此选项，migrate不管目标redis是否存在该键都会正常迁移进行数据覆盖。
# [keys key [key ...]]：迁移多个键，例如要迁移key1、key2、key3，此处填写 keys key1 key2 key3
```

### 键管理-遍历键

#### 1 全量遍历键 keys

```sql
keys pattern
# 遍历所有的键 用keys *; 
# pattern使用的是glob 风格的通配符（https://www.jianshu.com/p/d9633bb74e9a）
# 键比较多的时候 慎用，会阻塞！！！
```



#### 2 渐进式遍历（scan）

```sql
scan cursor [match pattern] [count nubmer]
# cursor 是必需参数，实际上cursor是一个游标，第一次遍历从0开始，每次scan遍历都会返回当前游标的值，直到游标值为0，表示遍历结束
# match pattern是可选参数，它的作用是做模式的匹配，这点和keys的模式很像
# count number 是可选参数，它的作用是表名每次要遍历的键的个数，默认值是10，此参数可以适当增大

# 针对hgetall、smembers、zrange等产生的阻塞问题，对应的命令分别为：hscan、sscan、zscan。用法和scan基本类似
```

**PS：注意问题**

如果在scan的过程中，如果有键的变化（增加、删除、修改），那么遍历效果可能碰到如下问题：<span style="color:red">**新增的键可能没有遍历到，遍历出了重复的键等情况**</span>，也就是说scan并不能保证完整的遍历出来所有的键，这些是我们在开发的时候需要考虑的。

### 键管理-数据库管理

#### 1 切换数据库 select

```sql
select dbIndex
# dbIndex 默认是从0-15（共16个database）
```



#### 2 清除数据库flushdb/flushall

```sql
flushdb #清除当前数据库
flushall # 清除所有数据库
# 这两个操作会阻塞数据库，使用时需注意。
# 后面介绍 rename-command配置 防止误用这个flush的命令

```

## 慢查询相关命令

### 慢查询配置修改（config set）

```shell
config set slowlog-log-slower-than 20000
config set slowlog-max-len 1000
config rewrite
# 如果要redis将配置持久化到本地配置文件，需要执行config rewrite命令。
```

![](https://static01.imgkr.com/temp/902925088a6a47c8854bc44730e0d891.png)



### 慢查询命令

#### 1 获取慢查询日志 slowlog get

```shell
slowlog get [n]
# 参数n可以指定条数
```
样例如下：
![](https://static01.imgkr.com/temp/9472f04e2c1c4719a2636e1a75a6dfc1.png)

可以看到每个慢查询日志有4个属性组成，分别是**慢查询日志的标识id、发生时间戳、命令耗时、执行命令和参数**。

![](https://static01.imgkr.com/temp/12e720995da341c18e61b12eeb3aa473.png)

#### 2 获取慢查询日志列表当前的长度 slowlog len

```shell
slowlog len
# 获取长度，返回 整型
```



#### 3 慢查询日志重置 slowlog reset

```shell
slowlog reset
```



## Bitmaps常用命令

### 1 设置值 setbit

```shell
setbit key offset value
```

> 第一次初始化Bitmaps的时候，加入偏移量非常大，会造成redis阻塞

### 2 获取值 getbit

```shell
getbit key offset
# 返回键的第offset位的值（从0开始算）
# 返回0 说明没有访问过
```

### 3 获取Bitmaps指定范围值为1 的个数 bitcount

```shell
bitcount key [start][end]
```

### 4 bitmaps间的运算 bitop

```shell
bitop op destkey key[key ...]
# op 可以是不同的运算规则：and(交集)、or(并集)、not(非)、xor(异或)。

```

