------



# Zookeeper应用场景和深入进阶

## 1 Zookeeper应用场景

### 1.1 数据发布/订阅

- 数据发布/订阅（Publish/Subscribe）系统，即所谓的配置中⼼，顾名思义就是发布者将数据发布到ZooKeeper的⼀个或⼀系列节点上，供订阅者进⾏数据订阅，进⽽达到动态获取数据的⽬的，实现配置信息的集中式管理和数据的动态更新

- 发布/订阅系统⼀般有两种设计模式，分别是推（Push）模式和拉（Pull）模式。在推模式中，服务端主动将数据更新发送给所有订阅的客户端；⽽拉模式则是由客户端主动发起请求来获取最新数据，通常客户端都采⽤定时进⾏轮询拉取的⽅式

- ZooKeeper 采⽤的是推拉相结合的⽅式：客户端向服务端注册⾃⼰需要关注的节点，⼀旦该节点的数据发⽣变更，那么服务端就会向相应的客户端发送Watcher事件通知，客户端接收到这个消息通知之后，需要主动到服务端获取最新的数据

### 1.2 命名服务

- 命名服务（Name Service）也是分布式系统中⽐较常⻅的⼀类场景，是分布式系统最基本的公共服务之⼀。在分布式系统中，被命名的实体通常可以是集群中的机器、提供的服务地址或远程对象等——这些都可以统称它们为名字（Name），其中较为常⻅的就是⼀些分布式服务框架（如RPC、RMI）中的服务地址列表，通过使⽤命名服务，客户端应⽤能够根据指定名字来获取资源的实体、服务地址和提供者的信息等

- ZooKeeper 提供的命名服务功能能够帮助应⽤系统通过⼀个资源引⽤的⽅式来实现对资源的定位与使⽤。另外，⼴义上命名服务的资源定位都不是真正意义的实体资源——在分布式环境中，上层应⽤仅仅需要⼀个全局唯⼀的名字，类似于数据库中的唯⼀主键

- 在ZooKeeper中，每⼀个数据节点都能够维护⼀份⼦节点的顺序顺列，当客户端对其创建⼀个顺序⼦节点的时候 ZooKeeper 会⾃动以后缀的形式在其⼦节点上添加⼀个序号，在这个场景中就是利⽤了ZooKeeper的这个特性

### 1.3 集群管理

- 所谓集群管理，包括集群监控与集群控制两⼤块，前者侧重对集群运⾏时状态的收集，后者则是对集群进⾏操作与控制

- Zookeeper的两⼤特性：

    - 客户端如果对Zookeeper的数据节点注册Watcher监听，那么当该数据节点的内容或是其⼦节点列表发⽣变更时，Zookeeper服务器就会向订阅的客户端发送变更通知

    - 对在Zookeeper上创建的临时节点，⼀旦客户端与服务器之间的会话失效，那么临时节点也会被⾃动删除

- 利⽤其两⼤特性，可以实现集群机器存活监控系统，若监控系统在/clusterServers节点上注册⼀个Watcher监听，那么但凡进⾏动态添加机器的操作，就会在/clusterServers节点下创建⼀个临时节点：/clusterServers/[Hostname]，这样，监控系统就能够实时监测机器的变动情况

### 1.4 Master选举

- 在分布式系统中，Master往往⽤来协调集群中其他系统单元，具有对分布式系统状态变更的决定权

- 客户端集群在ZooKeeper上创建⼀个临时节点，例如/master_election/binding。在这个过程中，只有⼀个客户端能够成功创建这个节点，那么这个客户端所在的机器就成为了Master。同时，其他没有在ZooKeeper上成功创建节点的客户端，都会在节点/master_election 上注册⼀个⼦节点变更的 Watcher，⽤于监控当前的 Master 机器是否存活，⼀旦发现当前的 Master 挂了，那么其余的客户端将会重新进⾏Master选举

### 1.5 分布式锁

- 分布式锁是控制分布式系统之间同步访问共享资源的⼀种⽅式。如果不同的系统或是同⼀个系统的不同主机之间共享了⼀个或⼀组资源，那么访问这些资源的时候，往往需要通过⼀些互斥⼿段来防⽌彼此之间的⼲扰，以保证⼀致性，在这种情况下，就需要使⽤分布式锁了

### 1.6 分布式队列

- 分布式队列可以简单分为两⼤类：⼀种是常规的FIFO先⼊先出队列模型，还有⼀种是 等待队列元素聚集后统⼀安排处理执⾏的Barrier模型

## 2 Zookeeper深入进阶

### 2.1 ZAB协议

- ZAB协议并不像Paxos算法那样 是⼀种通⽤的分布式⼀致性算法，它是⼀种特别为zookeeper专⻔设计的⼀种⽀持崩溃恢复的原⼦⼴播协议

- 在zookeeper中，主要就是依赖ZAB协议来实现分布式数据的⼀致性，基于该协议，Zookeeper实现了⼀种主备模式的系统架构来保持集群中各副本之间的数据的⼀致性，表现形式就是 使⽤⼀个单⼀的主进程来接收并处理客户端的所有事务请求，并采⽤ZAB的原⼦⼴播协议，将服务器数据的状态变更以事务Proposal的形式⼴播到所有的副本进程中，ZAB协议的主备模型架构保证了同⼀时刻集群中只能够有⼀个主进程来⼴播服务器的状态变更，因此能够很好地处理客户端⼤量的并发请求。但是，也要考虑到主进程在任何时候都有可能出现崩溃退出或重启现象，因此,ZAB协议还需要做到当前主进程当出现上述异常情况的时候，依旧能正常⼯作

- ZAB协议的核⼼是定义了对于那些会改变Zookeeper服务器数据状态的事务请求的处理⽅式

    - 即：所有事务请求必须由⼀个全局唯⼀的服务器来协调处理，这样的服务器被称为Leader服务器，余下的服务器则称为Follower服务器，Leader服务器负责将⼀个客户端事务请求转化成⼀个事务Proposal（提议），并将该Proposal分发给集群中所有的Follower服务器，之后Leader服务器需要等待所有Follower服务器的反馈，⼀旦超过半数的Follower服务器进⾏了正确的反馈后，那么Leader就会再次向所有的Follower服务器分发Commit消息，要求其将前⼀个Proposal进⾏提交

- ZAB协议包括两种基本的模式：崩溃恢复和消息⼴播

    - 进⼊崩溃恢复模式：
    
        - 当整个服务框架启动过程中，或者是Leader服务器出现⽹络中断、崩溃退出或重启等异常情况时，ZAB协议就会进⼊崩溃恢复模式，同时选举产⽣新的Leader服务器。当选举产⽣了新的Leader服务器，同时集群中已经有过半的机器与该Leader服务器完成了状态同步之后，ZAB协议就会退出恢复模式，其中，所谓的状态同步 就是指数据同步，⽤来保证集群中过半的机器能够和Leader服务器的数据状态保持⼀致
        
        - 基本特性
        
            > ZAB协议需要确保那些已经在Leader服务器上提交的事务最终被所有服务器都提交  
            ZAB协议需要确保丢弃那些只在Leader服务器上被提出的事务
        
        - 所有正常运⾏的服务器，要么成为 Leader，要么成为 Follower 并和 Leader 保持同步。Leader服务器需要确保所有的Follower服务器能够接收到每⼀条事务Proposal，并且能够正确地将所有已经提交了的事务Proposal应⽤到内存数据库中去。具体的，Leader服务器会为每⼀个Follower服务器都准备⼀个队列，并将那些没有被各Follower服务器同步的事务以Proposal消息的形式逐个发送给Follower服务器，并在每⼀个Proposal消息后⾯紧接着再发送⼀个Commit消息，以表示该事务已经被提交。等到Follower服务器将所有其尚未同步的事务 Proposal 都从 Leader 服务器上同步过来并成功应⽤到本地数据库中后，Leader服务器就会将该Follower服务器加⼊到真正的可⽤Follower列表中，并开始之后的其他流程
    
    - 进⼊消息⼴播模式：
    
        - 当集群中已经有过半的Follower服务器完成了和Leader服务器的状态同步，那么整个服务框架就可以进⼊消息⼴播模式，当⼀台同样遵守ZAB协议的服务器启动后加⼊到集群中，如果此时集群中已经存在⼀个Leader服务器在负责进⾏消息⼴播，那么加⼊的服务器就会⾃觉地进⼊数据恢复模式：找到Leader所在的服务器，并与其进⾏数据同步，然后⼀起参与到消息⼴播流程中去。Zookeeper只允许唯⼀的⼀即：所有事务请求必须由⼀个全局唯⼀的服务器来协调处理，这样的服务器被称为Leader服务器，余下的服务器则称为Follower服务器，Leader服务器负责将⼀个客户端事务请求转化成⼀个事务Proposal（提议），并将该Proposal分发给集群中所有的Follower服务器，之后Leader服务器需要等待所有Follower服务器的反馈，⼀旦超过半数的Follower服务器进⾏了正确的反馈后，那么Leader就会再次向所有的Follower服务器分发Commit消息，要求其将前⼀个Proposal进⾏提交个Leader服务器来进⾏事务请求的处理，Leader服务器在接收到客户端的事务请求后，会⽣成对应的事务提议并发起⼀轮⼴播协议，⽽如果集群中的其他机器收到客户端的事务请求后，那么这些⾮Leader服务器会⾸先将这个事务请求转发给Leader服务器
    
        - 具体的过程：在消息⼴播过程中，Leader服务器会为每⼀个Follower服务器都各⾃分配⼀个单独的队列，然后将需要⼴播的事务 Proposal 依次放⼊这些队列中去，并且根据 FIFO策略进⾏消息发送。每⼀个Follower服务器在接收到这个事务Proposal之后，都会⾸先将其以事务⽇志的形式写⼊到本地磁盘中去，并且在成功写⼊后反馈给Leader服务器⼀个Ack响应。当Leader服务器接收到超过半数Follower的Ack响应后，就会⼴播⼀个Commit消息给所有的Follower服务器以通知其进⾏事务提交，同时Leader⾃身也会完成对事务的提交，⽽每⼀个Follower服务器在接收到Commit消息后，也会完成对事务的提交

- 运⾏时状态分析
    
    - 在ZAB协议的设计中，每个进程都有可能处于如下三种状态之⼀
      　
        - LOOKING：Leader选举阶段

        - FOLLOWING：Follower服务器和Leader服务器保持同步状态

        - LEADING：Leader服务器作为主进程领导状态
    
    - 所有进程初始状态都是LOOKING状态，此时不存在Leader，接下来，进程会试图选举出⼀个新的Leader，之后，如果进程发现已经选举出新的Leader了，那么它就会切换到FOLLOWING状态，并开始和Leader保持同步，处于FOLLOWING状态的进程称为Follower，LEADING状态的进程称为Leader，当Leader崩溃或放弃领导地位时，其余的Follower进程就会转换到LOOKING状态开始新⼀轮的Leader选举
    
    - ⼀个Follower只能和⼀个Leader保持同步，Leader进程和所有的Follower进程之间都通过⼼跳检测机制来感知彼此的情况。若Leader能够在超时时间内正常收到⼼跳检测，那么Follower就会⼀直与该Leader保持连接，⽽如果在指定时间内Leader⽆法从过半的Follower进程那⾥接收到⼼跳检测，或者TCP连接断开，那么Leader会放弃当前周期的领导，并转换到LOOKING状态，其他的Follower也会选择放弃这个Leader，同时转换到LOOKING状态，之后会进⾏新⼀轮的Leader选举

- ZAB与Paxos的联系和区别

    - 联系：
    
        - 都存在⼀个类似于Leader进程的⻆⾊，由其负责协调多个Follower进程的运⾏。
    
        - Leader进程都会等待超过半数的Follower做出正确的反馈后，才会将⼀个提议进⾏提交。
    
        - 在ZAB协议中，每个Proposal中都包含了⼀个epoch值，⽤来代表当前的Leader周期，在Paxos算法中，同样存在这样的⼀个标识，名字为Ballot
    
    - 　区别：
    
        - Paxos算法中，新选举产⽣的主进程会进⾏两个阶段的⼯作，第⼀阶段称为读阶段，新的主进程和其他进程通信来收集主进程提出的提议，并将它们提交。第⼆阶段称为写阶段，当前主进程开始提出⾃⼰的提议
    
        - ZAB协议在Paxos基础上添加了同步阶段，此时，新的Leader会确保 存在过半的Follower已经提交了之前的Leader周期中的所有事务Proposal。这⼀同步阶段的引⼊，能够有效地保证Leader在新的周期中提出事务Proposal之前，所有的进程都已经完成了对之前所有事务Proposal的提交
    
    - 总的来说，ZAB协议和Paxos算法的本质区别在于，两者的设计⽬标不太⼀样，ZAB协议主要⽤于构建⼀个⾼可⽤的分布式数据主备系统，⽽Paxos算法则⽤于构建⼀个分布式的⼀致性状态机系统

### 2.2 服务器⻆⾊

- Leader：Leader服务器是Zookeeper集群⼯作的核⼼，其主要⼯作有以下两个：

    - 事务请求的唯⼀调度和处理者，保证集群事务处理的顺序性
  　
    - 集群内部各服务器的调度者

- Follower：Follower服务器是Zookeeper集群状态中的跟随者，其主要⼯作有以下三个：

    - 处理客户端⾮事务性请求（读取数据），转发事务请求给Leader服务器

    - 参与事务请求Proposal的投票

    - 参与Leader选举投票
    
- Observer：Observer服务器在⼯作原理上和Follower基本是⼀致的，对于⾮事务请求，都可以进⾏ᇿ⽴的处理，⽽对于事务请求，则会转发给Leader服务器进⾏处理。和Follower唯⼀的区别在于，Observer不参与任何形式的投票，包括事务请求Proposal的投票和Leader选举投票

### 2.3 leader选举

- 当Zookeeper集群中的⼀台服务器出现以下两种情况之⼀时，需要进⼊Leader选举

    - 服务器初始化启动

    - 服务器运⾏期间⽆法和Leader保持连接

- 服务器启动时期的Leader选举

    - 每个Server发出⼀个投票
    
        - 由于是初始情况，Server1（假设myid为1）和Server2假设myid为2）都会将⾃⼰作为Leader服务器来进⾏投票，每次投票会包含所推举的服务器的myid和ZXID，使⽤(myid, ZXID)来表示，此时Server1的投票为(1, 0)，Server2的投票为(2, 0)，然后各⾃将这个投票发给集群中其他机器
        
    - 接受来⾃各个服务器的投票

        - 集群的每个服务器收到投票后，⾸先判断该投票的有效性，如检查是否是本轮投票、是否来⾃LOOKING状态的服务器
    
    -  处理投票
    
        - 优先检查ZXID。ZXID⽐较⼤的服务器优先作为Leader
        
        - 如果ZXID相同，那么就⽐较myid。myid较⼤的服务器作为Leader服务器
    
    - 统计投票

        - 每次投票后，服务器都会统计所有投票，判断是否已经有过半的机器接收到相同的投票信息。对于Server1和Server2服务器来说，都统计出集群中已经有两台机器接受了（2，0）这个投票信息。这⾥我们需要对“过半”的概念做⼀个简单的介绍。所谓“过半”就是指⼤于集群机器数量的⼀半，即⼤于或等于（n/2+1）。对于这⾥由3台机器构成的集群，⼤于等于2台即为达到“过半”要求
    
    - 改变服务器状态
    
        - ⼀旦确定了 Leader，每个服务器就会更新⾃⼰的状态：如果是 Follower，那么就变更为FOLLOWING，如果是Leader，那么就变更为LEADING

- 服务器运⾏时期的Leader选举

    - 变更状态
    
        - Leader挂后，余下的⾮Observer服务器都会将⾃⼰的服务器状态变更为LOOKING，然后开始进⼊Leader选举过程
    
    - 每个Server会发出⼀个投票
    
        - 在运⾏期间，每个服务器上的ZXID可能不同，此时假定Server1的ZXID为123，Server3的ZXID为122；在第⼀轮投票中，Server1和Server3都会投⾃⼰，产⽣投票(1, 123)，(3, 122)，然后各⾃将投票发送给集群中所有机器
    
    - 接收来⾃各个服务器的投票
    
    - 处理投票
    
    - 统计投票
    
    - 改变服务器的状态