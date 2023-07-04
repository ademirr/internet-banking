# internet-banking
Internet Banking Santander


Tecnologias utilizadas:

Java 17, Maven, Spring Boot 3.1.1, H2, SpringDocs.

Na raiz do projeto estão os arquivos de carga de dados pra rodar no banco H2 (carga.txt) e o arquivo IB Santander.postman_collection.json que é a coleção de api's para importar no postman.

Para acessar o banco de dados (que é em memória), acessar a URL "http://localhost:8080/h2-console". Utilizar as propriedades que estão no arquivo "src\main\resources\application.properties" para logar no banco.

Utilizei o springdoc para exibir a documentação da API, que pode ser acessada pelo endereço http://localhost:8080/swagger-ui/index.html quando a aplicação estiver rodando.

Fiz a implementação em camadas (controller, service, domain e infra).

Fiz também testes unitários nas camadas controller e service.