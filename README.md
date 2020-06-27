# inventory-api
Essa api permite qualquer desenvolvedor criar inventários para seus plugins com muita facilidade.

### Por que usar essa api?
 - Seus inventários serem atualizados automaticamente com um tempo definido por você;
 - Existem 3 tipos diferentes de inventários, que são eles: únicos, paginados e globais;
 - Ações individuais para cada tipo de clique e ações padrões para cliques não registrados;
 - Eventos customizados facilitando incrementações extras;
 - Editor de inventário com métodos facilitando os moldes nos mesmos (centralizar itens, inserir itens em colunas ou linhas etc.);
 - Visualizadores podem ter propriedades customizadas definidas;
 - É fácil de usar e é código aberto;
 
 ### Instalação
 #### Maven
 ```html
 <!-- Adiciona o repositório jitpack para o projeto -->
 <repository>
 	<id>jitpack.io</id>
 	<url>https://jitpack.io</url>
 </repository>
 
 <!-- Adiciona o InventoryAPI como dependência para o projeto -->
 <dependency>
 	<groupId>com.github.HenryFabio</groupId>
 	<artifactId>inventory-api</artifactId>
 	<version>$version</version>
 </dependency>
 ```
 #### Gradle
```gradle
// Adiciona o repositório jitpack para o projeto
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

// Adiciona o InventoryAPI como dependência para o projeto
dependencies {
	implementation 'com.github.HenryFabio:inventory-api:$version'
}
```
**As versões disponíveis para utilização estão listadas em: [jitpack.io](https://jitpack.io/#HenryFabio/inventory-api).**

### Modo de uso
[Clique aqui para acessar o modo de uso desta api](https://github.com/HenryFabio/inventory-api/wiki/%231-Modo-de-Uso)