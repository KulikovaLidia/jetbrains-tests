# BUGS

## It is possible to assign a license when incorrect product type is sent
__*Description:*__

Method ```customer/licenses/assign``` requires in the body of POST request to send both license and license's product code.
So it is possible to send another product code (not from this licenses).

**Expected** -> license with inccorect product code won't be found and assign. 
            Response code 400, relevant error in the response body

**Actual** -> license is assigned, response code = 200

__*Automated tests to reproduce the problem:*__   
licenses.AssignLicensesTests.assignLicenseTest  
tests with descriptions :
    "Assign license from another product"
    "Assign license from invalid product"

## Internal server error when transfer "null" licenses to another team
__*Description:*__  
Method ```customer/changeLicensesTeam```.  
Send in the request body in the licenseIds list strings "null"

**Expected** -> Response code 400, relevant error that such licenses were not found  
**Actual** -> Response code 500

__*Automated tests to reproduce the problem:*__  
licenses.ChangeLicensesTeamTests.changeLicensesTeamTest  
test with description :  
"Move 'null' licenses to inactive team"

## 200 response code for incorrect licenses transfer
__*Description:*__   
Method ```customer/changeLicensesTeam```.  
Send in the request body in the licenseIds list :{empty strings, nothing, invalid licenses}

**Expected** -> Response code 400, relevant error that such licenses were not found
**Actual** -> Response code 200

__*Automated tests to reproduce the problem:*__   
licenses.ChangeLicensesTeamTests.changeLicensesTeamTest   
tests with descriptions :   
"Move empty licenses to inactive team"  
"Move no existed licenses to inactive team"   
"Move no licenses to inactive team"   

## Toolbox Enterprise EAP license is possible to transfer with API but not UI
__*Label:*__ To_be_discussed ; possible_bug

__*Description:*__
Method ```customer/changeLicensesTeam```.  
Toolbox Enterprise EAP license is not transferable in CRM with company admin rights.   
However, it is possible to transfer this license with API and company admin token.  
Requires further investigation. If such behaviour is expected, then test should be refactored.   

__*Steps to reproduce:*__   
Go to CRM under company admin, open Team001, choose Team Tools & Services tab, Toolbox Enterprise EAP license is here.   
It is not possible to select this license for transfer.   

__*Automated tests to reproduce the problem:*__   
licenses.ChangeLicensesTeamTests.changeLicensesTeamTest   
test with description :   
"Move Toolbox Enterprise EAP license (non transferable license?)"   

## Move license between two teams under team admin
__*Label:*__ to_be_discussed; possible_bug; documentation_update_required     
__*Description:*__   
Method ```customer/changeLicensesTeam```.     
Under team admin that has admin rules in both teams, move license from one team to another.   
It is passed in CRM, but failed using API method.  

Also in [transfer documentation](https://sales.jetbrains.com/hc/en-gb/articles/208460205-How-to-transfer-licenses-between-teams-) it is mentioned that to transfer a license you must be a company admin,    
but then in [roles and permissions](https://sales.jetbrains.com/hc/en-gb/articles/207739139-JetBrains-Account-administrator-and-user-roles) team admin is marked as allowed to transfer licenses

__*Steps to reproduce:*__     
Go to CRM under team admin, open a team, choose product with license, select license, transfer it to another available team.   
It is not possible to select this license for transfer.   

__*Automated tests to reproduce the problem:*__      
licenses.ChangeLicensesTeamTests.changeLicensesTeamTest      
test with description :      
"Move license from active to another team under team admin who has access to both teams"   

## Not possible to revoke licenses with API when it is okay in UI
__*Label:*__ to_be_discussed ; possible_bug

__*Description:*__   
Method ```customer/licenses/revoke```.   
Using the API POST request to revoke recently assigned license returns:   
>{"code":"RECENTLY_ASSIGNED_LICENSE_IS_NOT_AVAILABLE_FOR_REVOKE","description":"<license_id>"}

When it is passed in CRM.

__*Steps to reproduce:*__   
Go to CRM under company admin, open Team001, choose product that has assigned licenses, open licenses tab,   
choose an assigned license, open manage menu, revoke this license -> License is available to be assigned again.   

## Not able to send email when invite a user to a team
__*Steps to reproduce:*__   
 - Go to CRM under any admin
 - open a team tab
 - open administration tab
 - choose invite viewer or admin
 - agree to the permissions by selecting the checkbox
 - push on "Go to email client"
 - choose your email client
 - login

**Expected** -> new email is prefilled with invitation link and possible to be sent   
**Actual** -> after login an error "This account already exists"

__*Environment:*__ macOs, built-in safari email client, google email

## Automatically saved name of a team in CRM
__*Label:*__ to_be_discussed; documentation_update_required   

__*Description:*__  
Currently new name entered in the text field is saved automatically after few sec.  
This is not documented in [creating a team page](https://sales.jetbrains.com/hc/en-gb/articles/207739219-Creating-a-team) and can be misleading.

From my point of view, this should be either properly described in the user-guide documentation or changed with **Save** button.   

## Merge teams under team admin
__*Label:*__ to_be_discussed; documentation_update_required

__*Description:*__  
It is mentioned in [merge teams](https://sales.jetbrains.com/hc/en-gb/articles/208460175-How-to-merge-two-teams-) documentation that only company admin is allowed to merge teams.   
But then in [roles and permissions matrix](https://sales.jetbrains.com/hc/en-gb/articles/207739139-JetBrains-Account-administrator-and-user-roles) team administrator is marked as allowed as well.  
Documentation is required to be updated.   