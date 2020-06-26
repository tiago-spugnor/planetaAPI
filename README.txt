//--------------------------//
//	API PLANETAS SW	    //
//--------------------------//

A API aqui apresentada está em deploy no Heroku. Para acessá-la, basta utilizar o endereço >> https://api-planeta.herokuapp.com/ <<

As propriedades da aplicação estão setadas para apontar para a base de dados estabelecida no HEROKU. Caso deseje clonar este projeto, lembre-se de alterar os
valores apresentados em >> src/main/resources/application.properties << e >> src/main/resources/application-test.properties << para os respectivos endereços dos bancos locais.

Este projeto oferece os seguintes serviços em suas respectivas rotas:


- Obter todos os planetas de SW cadastrados: 				/planetas (GET)
- Buscar todos os planetas de SW pelo nome (ou que contenham ele): 	/planetas?search={nome (ou parcial) do planeta} (GET)
- Buscar planeta de SW exatamente pelo nome:				/planetas/nome/{nome do planeta} (GET)
- Buscar planeta de SW exatamente pelo identificador: 			/planetas/id/{valor do id} (GET)
- Remover planeta de SW do cadastro: 					/planetas/{valor do id} (DELETE)

- Atualizar planeta de SW no cadastro: 					/planetas/{valor do id} (PUT)
	- Deve conter no corpo da requisição um objeto JSON na forma de: {"clima" : "Deserto","terreno" : "Esburacado"}
	- Não é permitido alterar a ID do planeta nem seu nome.
	- Apenas os campos que são desejadas alterações precisam ser repassados.

- Adicionar planeta de SW ao cadastro: 					/planetas/ (POST)
	- Deve conter no corpo da requisição um objeto JSON na forma de: {"nome" : "Tatooine","clima" : "Deserto","terreno" : "Esburacado"}
	- Não é permitido adicionar um planeta ao cadastro que não tenha citação em um dos filmes de SW
	- Não é permitido inserir duplicatas dentro do cadastro (planetas de nomes iguais)
	- Os campos NOME, CLIMA e TERRENO são obrigatórios. 