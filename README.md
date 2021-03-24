## Clean Architecture for Enterprise Mobile Application

It has been over an year i am using Clean architecture for enterprise mobile applications. However i feel now the time has arrived to update the architectural template as my existing boilerplate is extensively using Rxjava (I am a big fan of Rxjava though :)). Even i can't  believe my self as i am moving from RxJava to Kotlin Coroutines. I was exploring coroutine and flow since last couple of months, and i am convinced enough to make a shift from Rxjava to Coroutine and flow.

This boilerplate project is created to serve as an architectural template which can be used for highly scalable enterprise mobile applications.

Below are the design considerations that i have take while creating the boilerplate project.

- Independent of framework
- Testable
- Independant of UI
- Independant of Database 
- Independant of any external agency

Keeping all these principles in mind, Clean architecture will be a great fit for any software application. Having that said Clean architecture will not be appropriarte for every project. So its down to you to decide whether or not it fits ur needs.

# Architecture

The architecture of the application strictly complies the following points.

- A single-activity architecture, using the Navigation component to manage fragment operations.
- Android architecture components, part of Android Jetpack for give to project a robust design, testable and maintainable.
- Model-View-ViewModel (MVVM) facilitating a separation of development of the graphical user interface.
- S.O.L.I.D design principles intended to make software designs more understandable, flexible and maintainable.
- Modular app architecture allows to be developed features in isolation, independently from other features.

## Modules

The following diagram represents the various module and their relationship.

<img src="/screenshots/app_modules.png" alt="Home"/>

- Application depends on core module and feature modules
- :core module is one of the main module in the application, where you can find all the common implementation. It contains the base classes + utils classes.
- :domain and :data modules are the heart of the application. This is where you can find out all the business logic.
  :domain module is completely a kotlin module and its independant of any other component in the architecture. The domain module contains all the business use cases and also it has the abstraction of the data repository.
  :data module contains all the data sources of the application, which includes local (db/file) and remote data source. 
- :thirdpartys is a module where you could find all the thirdpaty libraries integration. Each library has an abstraction layer which hides the actual implementation of the library, which creates a loose coupling between the components.
  This will be helpful if you decide to replace one library with another in the future.
- :features modules represents various features in the application. Each feature module can be delivered as dynamic feature modules in future if needed.
  Each of the feature module depends on core module, business module and thirdparty library modules.
  
  
## Architecture components

This boilerplate is extensively using architecture components such as databinding, ViewModel, Livedata ,etc. The template is made in such a way that the ViewModel shouldn't know anything about Android.
This improves testability, leak safety and modularity. ViewModels have different scopes than activities or fragments. While a ViewModel is alive and running, an activity can be any of its lifecycle states.
Activities and Fragments can be recreated again while the ViewModel is unaware.

So that merely means ViewModel has a longer lifecycle, so passing a reference of view to a ViewModel is a serious risk. Lets assume the ViewModel requests data from the network and the data comes back some times later.
May be at that time the view reference might be destroyed or might be an old activity thats no longer visible, in that case it might generate a memory leak and possibly a crash.

### Livedata and Databinding

I have been using databinding extensively throughout all my projects and i am a great fan of it. Databinding brings a balance between View and ViewModel (Someone might not agree with me on this :D)
When i was starting this boilerplate, there was a confusion whether to go with databinding or viewbinding, both are for the similar purpose. The additional advantage of databinding is that you can bind data directly in your view xml, so that means
you don't need to access a view in the Fragment / Activity to set the data which eliminates lots of boilerplate code. This is one of the main reason why i chose databinding over viewbinding. If you are using databinding in ur project,
you can make sure that your view class will not grow beyond a size as you are distributing ur logic with view xml, View class and ViewModel. 

Since Google has introduced architectural components, LiveData is the solution for reactive stream and its lifecycle aware too. But in this template i am using a combination of two reactive streams which is Kotlin Flow and LiveData.
Kotlin flow is being used a reactive stream in my business layer and the same has been converted to LiveData in the ViewModel to support databinding. Kotlin flow support for databinding is in alpha now, once that becomes stable we can remove LiveData completely.

### Kotlin Coroutines

As said earlier i was using Rxjava quite a lot in most of my projects , but i liked the structured concurrency in coroutines very much. Kotlin coroutine with flow can be great solution for
multithreading and reactive streams. Below diagram represents the pattern that we follow in this architectural template.

<img src="/screenshots/arch.png" alt="Home"/>

Points to note:

 1. ViewModel and repository layer is observing a flow object
 2. View is observing a LiveData object
 
LiveData is an observable data holder that is lifecycle aware. It helps with problems or issues which circles around lifecycle of a component. Additionally, it can be updated from any thread. This brings the next question —

### Why do we need Flow? Can’t we use either Flow or LiveData or Do we need both?
#### Why Flow over LiveData
LiveData was never designed to be fully-fledged reactive control. It’s good for one shot operation where we get all the data in one shot. But if you are getting a data stream or you are getting data in multiple packets, flow would be the right choice.
In case of LiveData, if you want to perform certain transformation, by default they would run on main thread. so if you have to dispatch the transformation on different thread, you probably would want to use suspend functions. Additionally, LiveData has a very small set of built-in operators while flow comes up with lots of them.

#### Why LiveData over Flow
LiveData caches the result. Let’s say, you fetch a result and it is stored in a LiveData object. Now user rotates the device to landscape mode, the activity will be recreated. Since the result was already fetched while device was in portrait mode, LiveData would emit the same result again so we would not need to refetch the results. In case of flow, it does not cache the result so in the same scenario we have to refetch the results, which is why it’s suggested to use LiveData between the view and view model layer.
LiveData takes care of data binding which means every-time there is data change, it can be observed in the view XML itself directly and the object does not need to observed in UI class of component. This is a kind of add-on in LiveData.

#### Golden rules of ViewModel and LiveData

- Keep the logic in Activities and Fragments to a minimum
- Don’t let ViewModels (and Presenters) know about Android framework classes
- Avoid references to Views in ViewModels.
- Instead of pushing data to the UI, let the UI observe changes to it.
- Distribute responsibilities, add a domain layer if needed.
- Add a data repository as the single-point entry to your data
- Expose information about the state of your data using a wrapper or another LiveData.
- Consider edge cases, leaks and how long-running operations can affect the instances in your architecture
- Don’t put logic in the ViewModel that is critical to saving clean state or related to data. Any call you make from a ViewModel can be the last one.
- Whenever you think you need a Lifecycle object inside a ViewModel, a Transformation is probably the solution.
- You don’t usually extend LiveData. Let your activity or fragment tell the ViewModel when it’s time to start loading data


## How navigation works in a multi-modular project ?

As you might have noticed, the boilerplate is based on multi modular architecture. There is an app module(which is the main module), and this app module depends on various feature modules.
There were lots of unknowns when i started thinking about how to use navigation jetpack in a multimodular project. Thanks to the awesome android community, i have referred lots of articles and finally i am able to find answer to all my questions.
Let me detail about the questions that i had in my mind when i was thinking about the same ?

- Should i include all the navigation logic into a single graph ?
- If i am using multiple navigation graph (per each feature module), how to navigate to other modules navigation graph ?
- How to go back to a fragment which belongs to a different feature module ?
- How to navigate to a fragment which belongs to another feature module, but that's not the start destination ? 

### Design Consideration

I have decided to use nested navigation graph approach. So here i have a main navigation graph which is located in the app module and then i have individual navigation graph for each feature modules.
The main navigation graph includes all other child navigation graphs.

<img src="/screenshots/main_nav_graph.png" alt="Navigation Graph"/>

We have 2 feature modules here, an onboarding module and a profile module. The onboarding module contains two fragments such as Splash and Login.

<img src="/screenshots/onboarding_nav_graph.png" alt="Onboarding Navigation Graph"/>

Similarly the profile module contains Profile and Edit profile frgments.

<img src="/screenshots/profile_nav_graph.png" alt="Profile Navigation Graph"/>

#### How to navigate to other modules navigation graph ?

The answer to the questions is through IDs. You can define a unique resource id in an xml. Using the name you provide in <item> element, the android 
developer tools create a unique integer in your projects R.java class, which you can use as an identifier for application resources or a unique integer
for use in application.

So here in our context we need to navigate from our LoginFragment(which belongs to onboarding feature module) to ProfileFragment (start destination of profile feature module),
since both of these fragment belongs to different feature module, we can't directly reference the one in profile feature module from onboarding. 

<img src="/screenshots/module_to_module.png" alt=""/>

But using the ID, we can refer to another navigation graph from our onboarding module. As you can see above, i am linking that particular id as the destination in the action tage.
The specific id is mentioned in the ids.xml like below.

<img src="/screenshots/ids.png" alt="IDs"/>

The same id has been used as an identifier for the nav_graph_profile, like below. So when you are referring this ID as a denstination, it will link the corresponding navigation graph and will be navigated to
the start destination of that particluar graph. In this case it will be profileFragment.

<img src="/screenshots/profile.png" alt="IDs"/>

#### How to go back to a fragment which belongs to a different feature module ?

Lets suppose if we want to go back to Login screen from Edit profile screen which belongs to the profile feature module. 
In this situation also we an use id. 

<img src="/screenshots/goback.png" alt=""/>

<img src="/screenshots/profile_ids.png" alt=""/>

##### onboarding feature module

<img src="/screenshots/onboarding_login.png" alt=""/>

#### How to navigate to a fragment which belongs to another feature module, but that's not the start destination ?

Lets take an example, think of a case where we have to navigate to edit profile fragment from Login screen.

When you have nested navigation graphs, deeplinking is the way for such use cases. Since we have to navigate to edit profile screen, 
we need to define a deeplink to that particular destination like below.

<img src="/screenshots/profile_deeplink.png" alt=""/>

##### onboarding feature module
<img src="/screenshots/deeplink_onboarding.png" alt=""/>

## Tech stack

-   [Jetpack](https://developer.android.com/jetpack):
    -   [Android KTX](https://developer.android.com/kotlin/ktx.html) - provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    -   [AndroidX](https://developer.android.com/jetpack/androidx) - major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    -   [Benchmark](https://developer.android.com/studio/profile/benchmark.html) - handles warmup, measures your code performance, and outputs benchmarking results to the Android Studio console.
    -   [Data Binding](https://developer.android.com/topic/libraries/data-binding/) - allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
    -   [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
    -   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
    -   [Navigation](https://developer.android.com/guide/navigation/) - helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.
    -   [Room](https://developer.android.com/topic/libraries/architecture/room) - persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
    -   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
-   [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - managing background threads with simplified code and reducing needs for callbacks.
-   [Koin](https://github.com/InsertKoinIO/koin) - dependency injector for replacement all FactoryFactory classes.
-   [Retrofit](https://square.github.io/retrofit/) - type-safe HTTP client.
-   [Coil ](https://github.com/coil-kt/coil) - image loading library for Android backed by Kotlin Coroutines.
-   [GSON](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.
-   [Timber](https://github.com/JakeWharton/timber) - a logger with a small, extensible API which provides utility on top of Android's normal Log class.
-   [Stetho](http://facebook.github.io/stetho/) - debug bridge for applications via Chrome Developer Tools.



