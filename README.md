# Descrição
Um projeto com objetivo de estudos baseado na arquitetura de microserviços, onde cada um é organizado usando arquitetura em camadas. A comunicação é síncrona, ou seja, comunicam-se através de requisições HTTP.

A ideia foi criar 2 microserviços: <b>Produto</b> e <b>Carrinho</b>. 

- No serviço de <b>Produto</b>, encontra-se um banco de dados onde são armazenados diferentes produtos com seus respectivos preços e quantidade em estoque. Além de possuir uma API para a comunicação.
- No serviço de <b>Carrinho</b>, temos os modelos <b>Cart</b> e <b>Item</b>. "Cart" representa um carrinho de compras que possui uma lista de itens como atributo. "Item" representa um item que será adicionado ao carrinho,
  contendo os atributos nome, preço unitário e quantidade.


# Análise técnica

### Comunicação
Para facilitar a comunicação entre os microserviços, usei diversas ferramentas. Entre elas estão: 
- <b>API Gateway</b> ➡️ É nele que chegam todas as requisições e é a partir dele que elas são distribuídas. Ele determina para qual serviço a requisição será encaminhada. Pode também fazer autenticações, autorizações
  para que centralize a lógica e responsabilidades comuns. Além de ter outros papéis, facilita a manutenção e escabilidade do sistema.

- <b>Discovery server (Netflix Eureka)</b> ➡️ É onde cada microserviço estará registrado e poderá se comunicar com diversos outros utilizando apenas o nome. Não será necessário saber o endereço e a porta em que estão rodando,
  o Eureka irá cuidar dessa parte. (Por padrão, é usado a porta 8765)
  
  O Eureka Server também se encarrega de realizar o <b>balanceamento de carga</b>, equilibrando o volume de requisições para aquele projeto que está instanciado mais de uma vez.

### Endpoints

Possui diversos endpoints que podem ser importados para o postman usando o arquivo JSON disponibilizado no repositório. Como o sistema usa <b>Eureka server</b>, todos os endpoints possuem o mesmo localhost (http://localhost:8765)

- <b>GET</b> (/ms-cart/cart) ➡️ Responsável por mostrar todos os itens dentro do carrinho, bem como o valor unitário e quantidade.
  
- <b>POST</b> (/ms-cart/cart) ➡️ Através do body, adiciona um item ao carrinho. Antes é feita a comunicação com o serviço de Produto para verificar a existencia do produto e se há estoque disponível.
  
- <b>GET</b> (/ms-cart/cart/total) ➡️ Responsável por somar o valor total que há no carrinho (quantidade * valor de cada item).
  
- <b>GET</b> (/ms-product/product) ➡️ Responsável por mostrar todos os produtos cadastrados, bem como o valor do produto e quantidade em estoque.

# Considerações finais

Graças a esse projeto, aprendi muito e pude desenvolver conhecimentos sobre a comunicação síncrona entre microserviços, uso do IntelliJ e Maven, POO, Java 8 (Streams, Lambda)

Vou ficar feliz em receber alguma mensagem com feedbacks sobre o projeto.
Se você perceber que errei em alguma coisa, que poderia melhorar em determinadas partes do código, por favor me manda uma mensagem, seria ótimo pro meu aprendizado!

