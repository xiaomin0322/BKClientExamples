## BKClientExamples

BookKeeper 客户端示例代码集

## 构建

环境要求：Java 8、Maven。

目前需要修改 `src/main/java/com/github/bewaremypower/config/DefaultConfig.java` 的 `ZK_SERVERS` 为 BookKeeper 集群所连接的 ZooKeeper 服务器，然后运行以下命令编译：

```bash
mvn clean package
```

部署 BookKeeper 集群，参考 [BookKeeper deploy](https://bookkeeper.apache.org/docs/latest/deployment/manual/)。

## 示例代码

见 `src/main/java/com/github/bewaremypower` 目录下的 `*.java` 文件，每个类都包含 `main` 方法，可独立运行。通过 Maven 运行的命令包装到了脚本 `bin/bookkeeper-cli` 中，通过命令行选项运行不同的示例。

### 1. WriteAndRead

```
./bin/bookkeeper-cli w
```

流程：创建 ledger，接收命令行输入，将每一行字符串的字节序列作为 entry 写入到创建的 ledger 中，输入 Ctrl+D 停止输入，之后会打开该 ledger，读取所有 entries 并打印。

### 2. DeleteLedgers

```
./bin/bookkeeper-cli d <MinLedgerId> <MaxLedgerId>
```

流程：删除闭区间 `[MinLedgerId, MaxLedgerId]` 的 id 对应的 ledgers

### 3. MultiThreadRead

```
./bin/bookkeeper-cli m <LedgerId> [ThreadNum]
```

流程：开启 `ThreadNum` 个线程，打开 `ledgerId` 对应的 ledger，读取所有 entries 并打印。


