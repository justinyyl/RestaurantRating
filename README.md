# Restaurant rating

## Project idea：
A restaurant rating application to serve restaurant bloggers, restaurant customers and all food lovers.
As a food lover, I know the importance of finding quality restaurants and dining experiences. 
I hope to be able to share my dining experience with others,
I also need a detailed rating system to help me decide whether to dine at a certain restaurant.<br>
<br>
Enter the name of a restaurant to view the restaurant's current ratings.
and rate the restaurant based on its **dishes** and **environment**.
Possible features include:
- Rate dishes based on taste, appearance and quality
- Rate the environment based on ambience, service quality and cleanliness
- Rate the restaurant based on dishes and environment



 
## User stories:

- As a user, I want to be able to create a new restaurant and add it to a list of restaurant
- As a user, I want to be able to select a restaurant and add a new dish to the restaurant
- As a user, I want to be able to select a dishes and add new scores of taste, appearance and quality
- As a user, I want to be able to select a restaurant and list all the dishes on that restaurant
- As a user, I want to be able to select a restaurant and add a new environment score to the restaurant
- As a user, I want to be able to add a dish and specify the taste score, appearance score and quality score
- As a user, I want to be able to view the rate the total score of selected dish
- As a user, I want to be able to view the average environment score of selected restaurant
- As a user, I want to be able to view the average dish score of selected restaurant
- As a user, I want to be able to view the score of the selected restaurant
- As a user, I want to be able to save my ResApp to file (if I so choose)
- As a user, I want to be able to be able to load my ResApp from file (if I so choose)



# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking
  any "Add..." button, and then type the required input
- You can generate the second required action related to the user story "showing multiple Xs in a Y" by clicking
  any "View..." button
- You can locate my visual component by starting the app
- You can save the state of my application by clicking the "Save restaurants to File" button
- You can reload the state of my application by clicking the "Load restaurants from File" button


# Phase 4: Task 2

- Tue Nov 28 13:10:29 PST 2023
  Add new restaurant: apm
- Tue Nov 28 13:10:43 PST 2023
  No dishes score for apm
- Tue Nov 28 13:10:47 PST 2023
  No environment score for apm
- Tue Nov 28 13:10:47 PST 2023
  No restaurant score for apm
- Tue Nov 28 13:11:02 PST 2023
  Add new dish without score: silver at restaurant: apm
- Tue Nov 28 13:11:12 PST 2023
  Add new score to existed dish: silver at restaurant: apm
- Tue Nov 28 13:11:22 PST 2023
  Add new dish without score: gold at restaurant: apm
- Tue Nov 28 13:11:31 PST 2023
  No dish score for gold
- Tue Nov 28 13:11:38 PST 2023
  Add new score for gold
- Tue Nov 28 13:11:40 PST 2023
  Average score for gold : 6.0
- Tue Nov 28 13:11:51 PST 2023
  Add new environment score at restaurant: apm
- Tue Nov 28 13:11:53 PST 2023
  Average environment score for apm : 6.0
- Tue Nov 28 13:11:54 PST 2023
  Average dishes score for apm : 5.666666666666666
- Tue Nov 28 13:11:56 PST 2023
  Average score for apm : 5.833333333333333


# Phase 4: Task 3

- I may separate RestaurantRating class into three distinct classes, namely AppUI, Restaurant, and DishUI. 
  The prevailing issue with the extant RestaurantRating class lies in its excessive complexity, 
  compounded by the user interface (UI) architecture it constructs across three distinct panels: ResApp, Restaurant, 
  and Dish. Notably, the functionalities within each panel are relatively autonomous. Consequently, I propose the 
  partitioning of this class into three discrete entities. This restructuring facilitates enhanced modularity and 
  ease of future enhancements to individual components.
- I may also create a View Interface in UI package, since the I separate the RestaurantRating class.
  In this condition, each class in UI package will has a view method. In this way, I can increase readability and 
  reduce coupling.





