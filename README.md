# Target2Sell Mobile Library

## Installation

### Android

Add the following to your dependencies :
```
TO BE DEFINED BY T2S
https://getstream.io/blog/publishing-libraries-to-mavencentral-2021/ 
https://developer.android.com/studio/build/maven-publish-plugin
```
### iOS
```
TO BE DEFINED BY T2S
https://developer.apple.com/documentation/swift_packages  
https://johnoreilly.dev/posts/kotlinmultiplatform-swift-package/  
https://github.com/ge-org/multiplatform-swiftpackage
```
## Usage

The SDK can be instantiated by calling the **Target2SellLibrary** class with a **LibraryConfiguration** object which takes as parameters :

- A String representing the customerId
- ApplicationContext for Android / NSObject for iOS in order to access preferences/user defaults
- A userAgent (defined below)
- A boolean representing enableCMP value (authorise the library to communicate with T2S backend, by default this boolean is **false**)
- A boolean representing displayDebugLogs value (it sets logs' minSeverity from **Error**(false) to **Debug**(true), by default this boolean is **false**)

The UserAgent can be created by calling the **Target2SellUserAgent** constructor class which takes 4 Strings as parameters :
- The application name
- The version of the application
- The package of the application
- The version of the build

### Android example
``` kotlin      
val userAgent = Target2SellUserAgent(  
  "ProjectName",  
  BuildConfig.VERSION_NAME,  
  this.packageName,  
  BuildConfig.VERSION_CODE.toString()
)  
      
val configuration = LibraryConfiguration(  
  context = applicationContext as ContextWrapper,  
  customerId = "myClientID"
  userAgent = userAgent,  
  enableCMP = true,  
  displayDebugLogs = false  
)  
      
val library = Target2SellLibrary(configuration)
```

Methods available to the user :
- Track displayed pages and clicks
- Get recommendations
- Get rank (in case you use the organise module)
- Enable/Disable CMP (authorise the library to communicate with T2S backend)

You can access the full documentation of the API here https://documentation.target2sell.com/index.php/fr/api

# Methods

Methods sendTracking, getRecommendations and useOrganiseModule are asynchronous functions which return a Resource<String>. This result this can be either a Resource.Success or a Resource.Error. \
You can unwrap the result the following way :
``` kotlin
runBlocking {
    withContext(Dispatchers.IO) {
        when(val result = library.sendTracking(trackingParameters)) {  
            is Resource.Success -> 	// Do something : Success value can be retrieved with "it.result" 
            is Resource.Error -> 	// Do something : Error value can be retrieved with "it.error" 
        }  
    }  
}
```


## Tracking of displayed pages and clicks (Tracking API)

The tracking is called by using the asynchronous function **sendTracking**  which takes as parameter a TrackingParameters object with the following elements :


|Parameter              |Type       |HTML                        										 |
|-----------------------|-----------|--------------------------------------------------------------------|
|pageId (**mandatory**) |Integer	| page Id Unique identifier of the page.           	                 |
|userEmail          	|String     | User's anonymized ID or encrypted mail address. This parameter is not mandatory but it is very important to fill when the user's email is known (user logged in). **Mandatory**  on « Purchase begin » and « Order Confirmation » pages  |
|userID          		|String     | User unique ID. **Mandatory**  on a « Purchase begin page ». You can put the same value as the userEmail parameter if you need to.  |
|eventType          	|String     | Possible values: view, click, mediaClick         		 |						
|spaceId          		|String     | space ID **Mandatory**  on a « click » event (see eventType parameter)   |
|productPosition        |String     | product position **Mandatory**  on a « click » event (see eventType parameter). Position of the clicked product in the recommendation space - the position of the first product is 0  |
|basketProduct          |String     | a formatted list of the product identifiers currently in the cart. Each product identifier has to be separated from the next one by "\|". Ex: p456\|p76\|p890\|p56 |	
|language          		|String     | language of the current page if your website has several ones.     |
|domain          		|String     | is  **Mandatory**  if the beginning of your website URL changes in fonction of your pages (i.e : www.mywebsite.com becomes product456.mywebsite.com when visiting the product 456 page) |
|itemID          		|String     | item id. List of products on the page. Each one of them should be separated by « \| ». Ex: p456\|p76\|p890\|p56. **Mandatory**  on a « Product page », « Cart page », « Search result page », « Post-payment page »       |
|categoryID          	|String     | id of the main category of a product list. **Mandatory**  on a « Category page »       |
|cartTotalAmount        |String     | total amount of the cart (VAT included). **Mandatory**  on a « Cart page », « Post-payment page »   |
|productQuantity        |String     | formatted list of the quantity of each product in the cart. Each identifier has to be separated from the next one by "\|" **Mandatory**  on a « Cart page », « Post-payment page » |
|keywords				|String     | keywords from search **Mandatory**  on a « Search result page »  |
|orderID          		|String     | unique identifier of the order placed by the visitor. **Mandatory**  on a « Post-payment page »  |
|priceList         		|String     | formatted list of the amount of each bought products. Each amount has to be separated from the next one by "\|", and equals the price of each product multiplied by the quantity. This parameter is necessary in case of a discount (in B2B mainly).  |
|userRank          	 	|String     | user rank obtained after a call to [the User Rank API](https://documentation.target2sell.com/user/index.html#api-Indexes-GetIndexesUsercookie)  |
|crm_XXX          		|String     | An additional CRM piece of information only known at runtime. Several values can be given (crm_XXX, crm_YYY, crm_ZZZ). Act as an override of CRM values already known by Target2sell for the user. |
|mediaRuleId            |String     | The media rule identifier for Media V1 |
|productCampaignId      |String     | The media campaign identifier for Media V2 **Mandatory**  when event type is mediaClick |
|mediaAlgo				|String     | The algorithm used to recommend the campaign clicked. **Mandatory**  when event type is mediaClick   | 	

### Android example
``` kotlin
val trackingParameters = TrackingParameters(pageId = 1000)
    
runBlocking {  
    withContext(Dispatchers.IO) {  
        when(val result = library.sendTracking(trackingParameters)) {  
            is Resource.Success -> 	// Do something : Success value can be retrieved with "it.result" 
            is Resource.Error -> 	// Do something : Error value can be retrieved with "it.error" 
        }  
    }  
}
```
## Products - Recommendation as JSON (Recommendation API)

Recommendations are retrieved by using the asynchronous function **getRecommendations**  which takes as parameter a RecommendationParameters class with the following elements :

|Parameter              |Type       |HTML                        										 |
|-----------------------|-----------|--------------------------------------------------------------------|
|locale (**mandatory**) |String		| depends on the country Ex: fr, nl, de           		     	 |
|pageId (**mandatory**) |String		| page Id Unique identifier of the page.       		       			 |         																	
|language          		|String     | language of the current page if your website has several ones.     |         
|itemID          		|String     | List of products on the page. Each one of them should be separated by « \| » Ex: p456\|p76\|p890\|p56 **Mandatory**  on a « Product page », « Add-to-cart », « Cart page », « Search result page », « Post-payment page »	|
|basketProducts			|String     | a formatted list of the product identifiers currently in the cart. Each product identifier has to be separated from the next one by "\|". Ex: p456\|p76\|p890\|p56     																			  | 
|categoryID          	|String     | id of the main category of a product list **Mandatory**  on a « List page »	 |
|contextURL 			|String     | URL of the page (context URL). This parameter is **mandatory** if you want to use constraints on URL.	 |											
|keywords				|String     | keywords from search                                               |
|setID          		|String     | set ID       													     |
|referer				|String     | referer												             |
|constraint          	|String     | The dynamic constraints actions to apply for the recommendation. This should be a json string compliant with the [following format](https://documentation.target2sell.com/index.php/fr/api)

### Android example
``` kotlin
val recommendationParameters = RecommendationParameters(locale = "fr", pageId = 1200)  
    
runBlocking {  
    withContext(Dispatchers.IO) {  
        when(val result = library.getRecommendations(recommendationParameters)) {  
            is Resource.Success -> 	// Do something : Success value can be retrieved with "it.result" 
            is Resource.Error -> 	// Do something : Error value can be retrieved with "it.error" 
        }  
    }  
}
```
## Get indexes for a user

In case you are using the Organise module you will have to call the asynchronous function **useOrganiseModule** with the following elements in order to retrieve a user's rank.

|Parameter              |Type       |HTML                        										 |
|-----------------------|-----------|--------------------------------------------------------------------|
|setID					|Integer	| page Id Unique identifier of the page.           					 |

This method needs to be called only once (at the initialisation of the SDK in your project), it stores the value inside the local storage of the phone.
Once executed and the result retrieved, you can :
-	Call the **getRank** function every time you need it : (it automatically retrieves the value from the local storage)
-	Retrieve the result of the function and store it yourself

### Android example
*Function useOrganiseModule*
``` kotlin
runBlocking {  
    withContext(Dispatchers.IO) {  
        when(val result = library.useOrganiseModule(RankParameters("12345678"))) {  
            is Resource.Success -> 	// Do something : Success value can be retrieved with "it.result" 
            is Resource.Error -> 	// Do something : Error value can be retrieved with "it.error" 
        }  
    }  
}
```
*Function getRank (to call only when useOrganiseModule has been called once)*
``` kotlin
val rank = library.getRank()
```

## Enable/Disable CMP
Communication from the library to T2S backend services can be enabled/disabled by using the following functions (once the library has been initialised) :
``` kotlin
library.enableCMP() 	// Allow the communication
library.disableCMP() 	// Disallow the communication
```


