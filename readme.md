# Documentação

## Baixa Todas as Dependências e Plugins Necessários
```bash
mvn dependency:go-offline -B
```
- `mvn dependency:go-offline` é uma goal do plugin de dependências do Maven que força o Maven a baixar **todas as dependências e plugins** do projeto **antes** da compilação ou execução, preparando o projeto para ser executado **offline** depois, ou seja, sem precisar baixar nada da internet durante as próximas execuções.
- O `-B` (batch mode) faz o Maven rodar em modo não interativo, ideal para CI/CD e scripts automatizados, pois evita prompts que esperam interação do usuário.


## Efetua uma Construção Limpa Pulando Todos os Testes
```bash
mvn clean package -DskipTests
```
- `clean`: Apaga a pasta `target` do projeto, onde ficam os arquivos compilados e gerados (como os JARs/WARs). Ou seja, limpa os artefatos da build anterior para começar do zero.
- `package`: Compila o código, roda os testes (a menos que estejam pulados) e empacota o projeto no formato configurado (por exemplo, gera um arquivo `.jar` ou `.war` na pasta `target`).
- `-DskipTests`: É uma propriedade que instrui o Maven a **não executar os testes** durante a fase de build. O código será compilado e o pacote gerado, mas os testes não serão rodados.


## Inicia o Ambiente Docker Sem Bloquear o Terminal
```bash
docker compose up -d
```
- `docker compose up`: sobe (inicia) todos os containers definidos no arquivo `docker-compose.yml`. Ele cria e inicia os serviços, redes e volumes definidos.
- `-d` **(detached mode)**: executa os containers em segundo plano (modo "desanexado"), ou seja, o terminal fica livre para você continuar usando enquanto os containers rodam em background.


## Executa um Comando Interativo Dentro de um Container em Execução
```bash
docker exec -it springboot bash
```
- O comando `docker exec` é usado para **executar um comando em um container que já está em execução.**
- `-i` (ou `--interactive`) mantém o **stdin aberto**, mesmo que não esteja conectado. Isso é crucial para interagir com o processo dentro do container.
- `-t` (ou `--tty`) **aloca um pseudo-TTY** (terminal). Isso é necessário para ter uma interface de linha de comando interativa.
- Juntos, `-it` permitem que você se conecte e interaja com um shell (como `bash`) dentro do container, como se estivesse acessando uma máquina virtual.
- `springboot` é o **nome ou ID do container** no qual você deseja executar o comando.
- `bash` é o **comando que será executado dentro do container**. Neste caso, inicia um shell Bash, permitindo que você execute outros comandos dentro do ambiente do container.