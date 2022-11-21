# Golden Raspberry Awards API
Projeto Golden Raspberry Awards API foi desenvolvido para mostrar e testar habilidades em Java usando [spring-boot](https://spring.io/projects/spring-boot).

O projeto:

    - O web service RESTful implementa o nível 2 de maturidade de Richardson;
    - Implementa testes de integração do controller garantindo que os dados obtidos estão de acordo com os dados fornecidos;
    - O banco de dados utilizado é embarcado (Banco H2);
    - É apaz de Ler, Gravar e Alterar Estúdios, Produtores e Filmes;
    - É capaz de obter o produtores com menor e maior intervalo entre dois prêmios consecutivos; 
    - É capaz na sua inicialização ler um arquivo CSV dos filmes e inserir os dados no Banco H2;
    - Contem tratamento e exceções;

## Teste online

- O projeto foi disponibilizado online e a API pode ser testado através do documentação da API no endereço [https://app-graapi.herokuapp.com/swagger-ui.html](https://app-graapi.herokuapp.com/swagger-ui.html)

## Instalação Local

- Necessário ter instalado e configurado o [Apache Maven](https://maven.apache.org/);
- Necessário ter instalado e configurado o [JAVA 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html);

## Executar a aplicação

- Na raiz do projeto execute:
```bash
.\mvnw clean package
```
- O Maven irá baixar as bibliotecas necessárias e gerar o executável na pasta target do projeto, basta executar o comando:
```bash
.\mvnw spring-boot:run
```

- Testes Unitários, na raiz do projeto execute:
```bash
.\mvnw test
```

- Testes de integração (IT), na raiz do projeto execute:
```bash
.\mvnw verify  
```
