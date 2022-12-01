# Sports Team Salary Manager

## Summary

This project is a system for tracking players payroll information on a sports team. It lets users specify a sport and 
enter information (personal and payroll information) about the players on their team. It will allow different sports
such as hockey, basketball, etc and will allow users to display information about player contracts and salary cap hits. 
The target audience for this system is sports team managers looking for a tool to keep track of their teams payroll and
player information. 

This project is of interest to me because I love to both play and watch sports. I am also interested in the management
of sports and how teams manage their budget when signing players to new contracts or extensions. Developing this project
will allow me to further explore the management of sports teams and the information that sports team managers would find
most important in a tool like this.

## User Stories

- As a user, I want to be able to create a team for a specified sport
- As a user, I want to be able to add a player to my team
- As a user, I want to be able to view a list of the players on my team
- As a user, I want to be able to select a player and view their contract
- As a user, I want to be able to view a list of the contracts of players on my team
- As a user, when I select the quit option from the application menu, I want to be reminded to save my 
team to a file and have the option to do so or not
- As a user, when I start the application, I want to be given the option to load my team from a file

# Instructions for Grader

- You can generate the first required event related to adding players to a team by loading/creating a team and 
clicking add player from the first page.
- You can generate the second required event related to adding players to a team by clicking the remove player button
from the page showing the list of players on the users team. 
- You can locate my visual component by clicking view contract of one player, or by clicking view all contracts.
The images are the dollar signs beside each contract.
- You can save the state of my application by clicking the save/load button in the top left corner and then 
clicking save, or by hiting the close button, where you will be prompted by a popup to save your team.
- You can reload the state of my application by clicking yes to the first popup that prompts the user to load a team
when the application starts, or at any time by clicking the save/load button and then clicking load from the main page.

### Phase 4: Task 2
Sample event log showing add and remove players from a team, and extending a players contract. When loading a team from
file, all players already on the team are logged as "added" because the add player event is called every time add player
is called, and add player is called for each player loaded in from the file.

Wed Nov 30 17:43:08 PST 2022  
Player DK added to Jets.  
Wed Nov 30 17:43:24 PST 2022  
Player Harry added to Jets.  
Wed Nov 30 17:43:37 PST 2022  
Player Johny added to Jets.  
Wed Nov 30 17:43:50 PST 2022  
Contract extended.  
Wed Nov 30 17:43:55 PST 2022  
Player Harry removed from Jets.  

### Phase 4: Task 3
The main refactoring I would do is to improve cohesion. Currently, my SportsTeam class has two responsibilities, dealing
with players, and dealing with salary information. I would add a new class that SportsTeam extends to move this 
salary behaviour to its own class to improve the cohesion here. I would also implement a bidirectional relationship
contract and player so that each contract is aware of the player that is attached to it. Currently, to extend or modify 
a players contract, methods are called on the players contract object, and not on the player themselves. By implementing
this relationship, it would make the relationship between a player and their contract more clear and mimic how contracts 
are extended in real life, and would also improve the programs logging as currently it is impossible for the program to 
report which players contract was extended since the class does not have access to this information. One final thing I 
could do to improve cohesion is to create three new classes to handle json/persistence related tasks for each of 
SportsTeam, Player, and Contract because all three of these classes currently have their own tasks of managing team, 
player, and contract information, along with handling how to save each class to json. I feel that this behaviour is 
separate enough for each class to extend their own persistence class that implements Writable in order to follow the 
single responsibility principle.
