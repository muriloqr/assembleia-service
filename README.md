# assembleia-service
Controle de votações de assembleias

## Iniciando

Necessário ter o Docker e Java devidamente instalado na máquina

Após clonar o projeto, importe na sua IDE de preferência para instalar as dependências ou então se preferir instale pela linha de comando dentro da pasta do projeto:

```
./gradlew clean build
```

Antes de rodar o projeto, é necessário que realize os passos abaixo:

#### Baixar kafka:

```
sudo docker pull spotify/kafka
```

#### Rodar kafka:

```
sudo docker run -d -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=localhost --env ADVERTISED_PORT=9092 --env KAFKA_ADVERTISED_HOST_NAME=localhost --env KAFKA_ADVERTISED_PORT=9092 --name kafka spotify/kafka
```

#### Criar tópico:

```
sudo docker exec kafka /opt/kafka_2.11-0.10.1.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic assembleia
```

#### Baixar postgres:

```
sudo docker pull postgres
```

#### Rodar postgres:

```
sudo docker run -p 5432:5432 --name assembleias -e POSTGRES_USER=assembleias -e POSTGRES_PASSWORD=senhaassembleias -e POSTGRES_DB=assembleias -d postgres
```


## Como eu versionaria a API:

Do princípio, parto de uma V1, em caso de ser necessária uma mudança na aplicação de uma forma em que alterar a V1 pode haver um impacto grande ou então não mensurado em outras aplicações que a consumam, criaria uma proxima versão criando packages com v2(ou a versão que for necessária) no final e mudaria na configuração do controller a versão, para que seja possível consumir tanto a v1 como a v2 (exemplo).
