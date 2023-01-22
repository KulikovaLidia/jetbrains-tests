# Testcases
## Team administration
### Updating team name
To change a team name, choose a team and open the Administration section.   
There is the text edit field, new name is saved automatically after you change/enter new values in few seconds.   
The hint 'Saved' will appear.

- Choose a team, update name -> name is updated, team is displayed with new name in the list of teams in the company section.
- Delete the name -> not able to save
- Newly created team -> name is automatically set to "No name"

__*Cover :*__
- Team : { active, inactive, new created }
- Input data : { Cyrillic, Latin, digits, symbols }
- 0 < Length <= 255
- Update team as : { Company administrator, Team administrator, Viewer } , where  
  Viewer not able to change name of a team   
  Team administrator is able to change name of its team

### Merging Teams
__*Prerequisites:*__   
Create few teams: two active, one inactive, add there users and different licenses   

- Go to active `Team B` , push merge button , choose existed active `Team A` ->   
`Team B` is merged, you are in the section of `Team A`.    
Check that all licenses with their state and users are moved from `Team B` are moved to `Team A`,   
`Team B` is deleted and not in the list anymore.

- Merge inactive `Team C` into active `Team A` -> not possible

__*Cover :*__   
- Team is inactive/active (Not able to merge in inactive team)
- Team has : { Users, Licenses expired/unassigned/assigned/different products, Invitations }   
- Merge team as : { Company administrator, Team administrator, Viewer } , where 
  Viewer that has one/few teams -> no merge button   
  Team administrator of one/few teams -> no merge button  


### User management (Administrator and Viewer roles only)
__*Prerequisites*__: Login as a company administrator, in administration section of any team of your company
- invite viewer -> with unselected checkbox it is not possible to send email

- invite viewer , select checkbox -> go to email (see bug in `BUGS.md`)
- invite viewer, select checkbox, copy text and login by that link -> viewer added to the team with viewer rights   
- invite team administrator, select checkbox, copy text and login by that link with viewer user -> user is updated to team administrator   
- invite team administrator, select checkbox, copy text and login by that link with new user -> user is added as a team administrator   
- invite team administrator, select checkbox, copy text and login by that link with a company admin user -> warning "You already have 'Administrator' permissions on the customer. Please check your JetBrains Account."   
- As team administrator downgrade other administrator to a viewer -> check that under this user administrator's rights are gone   
- As a team administrator leave a team -> check that team is not in your list anymore   
- As a team administrator revoke API token from viewer -> viewer has no token, not able to use API with old one   
- As a team administrator remove access from viewer -> viewer doesn't have this team anymore, check that if viewer had generated api token, than access is forbidden   
- Invite a team administrator , under this user generate a token -> check api rights. Change to viewer role -> check api method accesses changed   

__*Cover:*__
- Viewer not able to add new users and not able to leave team, able to see all licenses in the team.
- Team administrator is able to add new users.      
  Team administrator is able to leave the team and change roles of users in this team, purchase new licenses, upgrade existing licenses, distribute licenses.    
  But not able to merge teams    
- User is able to generates its own token and revoke, administrator is able to revoke other users' tokens
