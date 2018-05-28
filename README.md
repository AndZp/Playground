# Cryptocurrencies -  Android Playground: Sample App

#### This repository contains a detailed sample app that : 
- written in Kotlin (100%),  
- offline first strategy with repository patern 
- implements MVVM architecture using Dagger2, Room, RxJava, LiveData, ViewModel, Retrofit2 + Moshi, etc

#### The app has following packages:
1. **data**: It contains all the data accessing and manipulating components.
2. **di**: Dependency providing classes using Dagger2.
3. **ui**: View classes along with their corresponding ViewModel.
4. **utils**: Utility classes.

#### Classes have been designed in such a way that it could be inherited and maximize the code reuse.

### Tools: 
1. Android Studio 3.2 (Canary)

### Library reference resources:
1. RxJava2: https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
2. Dagger2: https://github.com/MindorksOpenSource/android-dagger2-example
3. Retrofit 2: http://square.github.io/retrofit
4. Moshi: https://github.com/square/moshi
5. Picasso: http://square.github.io/picasso/
6. LeakCanary: https://github.com/square/leakcanary
7. Timber: https://github.com/JakeWharton/timber

8. Android Jetpack https://developer.android.com/jetpack/  : 
   - Room: https://developer.android.com/topic/libraries/architecture/room
   - LiveData: https://developer.android.com/topic/libraries/architecture/livedata
   - ViewModel: https://developer.android.com/topic/libraries/architecture/viewmodel
   - Paging: https://developer.android.com/topic/libraries/architecture/paging/
   - Navigation : https://developer.android.com/topic/libraries/architecture/navigation/
     
#### Adb commands for deep link testing:
CryptoListFragment : 

```
adb shell am start 
   -a android.intent.action.VIEW 
   -d "http://mateam.io/crypto/list" io.mateam.playground
   ```
CryptoDetailsFragment ({crypto_id} - replace with id) :

```
adb shell am start 
   -a android.intent.action.VIEW 
   -d "http://mateam.io/crypto/details/{crypto_id}" io.mateam.playground 
  ```	
		
  
### License
```
 DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
         Version 2, December 2004 

 Copyright (C) 2004 Sam Hocevar <sam@hocevar.net> 

 Everyone is permitted to copy and distribute verbatim or modified 
 copies of this license document, and changing it is allowed as long 
 as the name is changed. 

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 

  0. You just DO WHAT THE FUCK YOU WANT TO.
  
```
<a href="http://www.wtfpl.net/"><img
       src="http://www.wtfpl.net/wp-content/uploads/2012/12/wtfpl-badge-4.png"
       width="80" height="15" alt="WTFPL" /></a>
       
