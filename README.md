# back-end

## Executando o projeto
`Localhost` <br>
- Baixe ou realize o clone do projeto no repositório do time,
- Copie e cole o `.env.example` na pasta raiz do projeto, retirando o `.example`,
- Copie as variáveis de ambiente [neste link](https://dontpad.com/dontenv-content) e cole dentro do `.env` que você criou,
- No terminal, escreva o comando ``docker compose up -d`` para subir o banco de dados (Container),
- Execute o projeto utilizando o comando `./mvnw spring-boot:run` ou execute o AppApplication

## Conexão com o banco de dados
### PostgreSQL
- URL: `jdbc:postgresql://dpg-cl47rghnovjs73bv05vg-a.oregon-postgres.render.com:5432/test_absentapp_mh8d`
- Username: `test_absentapp_user`
- Password: `GwgzuXozkqJWdHyJ36mchRQun0JwU6rH`

## Estrutura
O projeto do backend é estruturado utilizando o Maven e possui dois módulos separando a camada de entidades, repositórios e serviços da camada web. Respectivamente, os modulos são domain e api.

### Desenho da arquitetura
![image](https://github.com/absent-project/back-end/assets/85958572/1586c5fc-5808-4a7f-a7cb-0ab1bdd52e30)
- **Application Layer**: Controllers, o que recebe o dado (R) provindo do front-end.
- **Domain Layer**: Serviços, Repositórios e Entidades, onde o dado é tratado para um formato (T) onde será possível trabalhá-lo e aplicar a regra de negócio. No fim do processo, será devolvido um dado de resposta (Q).
- **Infrastracture Layer**: Base de dados, liquibase, e conexões a base de dados. Basicamente, tudo que o sistema precisa para funcionar.

### Módulos

#### Domain
O pacote Domain contém a estrutura abaixo: </br>
![image](https://github.com/absent-project/back-end/assets/85958572/d94c0efd-26f3-4bc7-8a97-bfcc92759e23)

Segue tabela de descrição de cada pacote na camada de domínio:
|   Pacote   |                                                        Descrição                                                            |
| ---------- | --------------------------------------------------------------------------------------------------------------------------- |
| core       | O pacote contém classes genéricas que auxiliam os desenvolvedores a criarem os repositórios, serviços e controllers.        |
| entity     | Pacote que contém as entidades (JPA) da aplicação.                                                                          |
| model      | Pacote que armazena os modelos ou DTO.                                                                                      |
| repository | Pacote que contém as classes de repositórios para consulta no banco de dados. Os repositórios seguem o padrão do SpringData |
| service    | Pacote onde são armazenados as classes de serviços                                                                          |

#### API
- O pacote API contém a estrutura abaixo: </br>
![image](https://github.com/absent-project/back-end/assets/85958572/2ea98ba0-ee5b-4e78-8535-508eccd11588)

Segue tabela de decrição de cada pacote na camada de API:
|   Pacote   |                                                        Descrição                                                                               |
| ---------- | ---------------------------------------------------------------------------------------------------------------------------------------------- |
| config     | Pacote com classes de configurações inerentes à arquitetura, como handler de excessões, Spring Security (não implementado) e Swagger Doc.      |
| controller | Pacote que contém os controladores (endpoints) da aplicação.                                                                                   |

### Links Importantes  
- Documentação (Swagger 3.0):
     - http://localhost:8080
          - Ambiente local (dev)
          - ⚠️ Necessário que a aplicação esteja rodando!
     - https://test-absentapp.onrender.com/swagger-ui/#/
          - Ambiente de testes, hospedado no Render.
          - Necessário apontar 'Servers' para o ambiente de desenvolvimento </br>
                 - ![image](https://github.com/absent-project/back-end/assets/85958572/9ecbddc3-bb62-4f0c-964f-1a8116a4f0fb)

-  Variáveis de ambiente (dotenv): [Variáveis](https://dontpad.com/dontenv-content)
