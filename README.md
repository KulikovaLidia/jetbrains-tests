# jetbrains-tests
JetBrains test assignment project that includes license assignment and license transfer tests.

To run tests:
>it is required to set up a config file:
>
 >- go to ```src/test/resources``` folder   
 >- rename ```ExampleTestConfg.properties``` file to ```TestConfg.properties```
 >- uncomment lines
 >- put required test data according to hints in the file  
 >
> then use command ```./gradlew test```.

Test report will be generated automatically in ```/build/reports/tests/test/index.html```

After test run, it is required to manually rollback data to correct test state:
- go to CRM under company admin user
- open Team0001 
- select Web Storm all licenses 
- sort them so that assigned will be on the top 
- select licenses ```{"V0IEECXWP2", "SK8TMYX9WU", "APT5835PAX", "AYUP1280JG"}```  
- revoke access

Testcases for Administration part are in ```TESTCASES.md``` file.   
All issues that were found while I was working on that task can be found in ```BUGS.md```
