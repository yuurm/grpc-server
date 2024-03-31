```bash
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
```
```bash
choco install protoc
```

```bash
protoc --plugin=protoc-gen-grpc-java=/path/to/grpc-java/compiler/build/exe/java_plugin/protoc-gen-grpc-java --grpc-java_out=. --proto_path=. phonebook.proto
```

```bash
C:\ProgramData\chocolatey\bin\protoc.exe --plugin=protoc-gen-grpc-java=/path/to/grpc-java/compiler/build/exe/java_plugin/protoc-gen-grpc-java --grpc-java_out=. --proto_path=. phonebook.proto

```