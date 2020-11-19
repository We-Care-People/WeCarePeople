Original App Design Project - README Template
===

# We Care People 

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Economic disadvantage is a global problem. Several households, stores dump basic items they don't need,(groceries, toys, shoes etc), in the trash almost every single day; on the other side, a large number of people are in need of those. Connecting the two different ends, people throwing items and people in need of those, can be a potential solution to fight this vicious economic challenge.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Social
- **Mobile:** Underprivileged people might not have a mobile; even they might possess a mobile but not familiar with using apps. However, somebody witnessing anybody without food on the street or anybody who knows that their neighbors or people living on the street or homeless people need food can post it in the app based on the location people can help.

- **Story:** Analyzes the area or zip code, then notify the people who need help. When somebody posts a need in the app, the users' available option would be shown based on the vicinity of his/her area. 

- **Market:** The young generation can contribute to a great extent. For example, if somebody has extra food, she/he wants to share but can not commute to a place where the food is needed. Anyone in the app voluntarily agrees to transport the goods.
- **Habit:** This app will be used often or unoften based on the condition of users, and how much they want to contribute to the social life.
- **Scope:** Everybody who is involved in this donation circle can influence others to do the same and somehow get recognized through the app.
    - Restaurant can also involve in the app. Many restaurants need to throw their food if not used in a day, they can also post in the app.
    - Although there is a risk of trusting random people involved. However, to register in the app, one has to verify their identity

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User login (using signin or signup) to access account

* The user (recipient or middle man) will check any user (Donor) in particular in his location already posted that they have similar stuff. 

* User (recipient) update what they need in a list of user profile
* User (Update) what they have in a list of user profile
* User (recipient) take a donate, that feed go to the board.
* Users can post about the items they want somebody to pick up.
* If User is not comfortable in sharing their address, a set up option for dropping off the item can be arranged.

**Optional Nice-to-have Stories**

* Direct message


### 2. Screen Archetypes

* [Register]
   * [user can register or sign up to access account]
   * ...
* [login]
   * [user can login to their account]
   * ...
* [Profile]
   * [user can update their profile]
   * 
* [Stream]
   * [user can view recent posts on the account]
   * 
* [Detail]
   * [user can read about the item posted]
   * 
* [Messages]
   * [user can send direct messages to Donor if they need the item posted] 
   * 
* [Maps]
   * [user can turn on location to see their drop off or pick-up point]
   * 
**Optional Nice-to-have Screen Achetypes**
* [Settings]
   * [user can modify app on the screen]



### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Post about what was donated
* Profile
* Cart

**Flow Navigation** (Screen to Screen)

* Force login (signin or signup)-> Account creation if no log in is available
* Update cart (Donor) for what items they have
* User (recipient) can search for what they need

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://github.com/We-Care-People/WeCarePeople/blob/main/wireframe.PNG" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
### Models

Post 
|Property|Type|Description|
|---|---|---|
|User|Pointer to User|image author|
|postId|String|unique id for the user post (default field)|
|item|Item|Image item|
|itemQuantity|String|Image item|
|likesCount|Integer|likes|
|Situation|Boolean|taken or not|
|Location|String|Location of post|
|createdAt|DateTime|date when post is created (default field)|
|updatedAt|DateTime|date when post is created (default field)|

Cart
|Property|Type|Description|
|---|---|---|
|item|List<Item>|Image item|

Item
|Property|Type|Description|
|---|---|---|
|id|String|id of item|
|Name|String|name of item|
|image|File|image of item|
|category|pointer|category of item|
|description|String|des|
|Situation|Boolean|taken or not|

|createdAt|DateTime|date when post is created (default field)|
|updatedAt|DateTime|date when post is created (default field)|

User
|Property|Type|Description|
|---|---|---|
|id|String|id of person|
|username|String|username|
|password|String|password|
|Name|String|name of person|
|image|File|image of person|
|donated Time // only for donator|number|donated time|
|Email|String|email|
|Location|String|Location of user|


Categories
|Property|Type|Description|
|---|---|---|
|id|String|id of person|
|name|String|username|
|image|String|password|



### Networking
Home:
Post:
- Get request - get a list of post about the items.
- Post request - create the post about the items.
- Delete request - delete the post about the items.
- Update request - update the post.

Cart:
Item:
- post request - create item
- get request - get item
- update request - updates items in the cart
