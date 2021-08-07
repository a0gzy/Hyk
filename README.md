# Hyk
Minecraft Mod

### Set JAVA_HOME as environment variable

### Getting started

1. Clone the repository
2. Setup the development environment
    ```shell script
    gradlew setupDecompWorkspace
    ```
3. Integrate the development environment with your IDE
    - IntelliJ IDEA
    ```shell script
    gradlew idea genIntellijRuns
    ```
   
4. Make sure to add the **VM** arguments to your debug configuration:

   **If you dont have them already!**
    
    ```text
    -Dfml.coreMods.load=me.a0g.hyk.tweaker.ASMTweaker
    ```
    
5. Include your **Minecraft** username and password as arguments in the **debug configuration**.
   In order, to login into [Hypixel](https://hypixel.net) by your account.
    ```text
    --username=<username> --password=<password>
    ```
   > **Note:** Don't share your password with **anyone**.
   > **We aren't going to ask you about your password!**
6.  Reload Gradle
7.  **You are now ready to build the mod!**
