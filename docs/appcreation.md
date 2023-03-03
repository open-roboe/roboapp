
These are the steps that were followed in order to create the roboapp

App built in feb. 2023, using
- java (even tho google now recommends kotlin. Uni project requirement)
- google architecture components and best practices. notably:
- mvvm architecture, with livedata and databinding
- single activity, multiple fragments. using the navigation architecture component
- material3

## create project

choose java as language, and "empty activity" as the default activity

## create navigation

resource manager > navigation tab > + icon

provide navigation graph name. when prompted for adding dependencies to gradle, choose yes.

In activity_main layout, add a NavHostFragment, and choose the navigation graph when prompted.

## material 3

remove the values-nigh folder.
in the values folder, replace colors.xml and theme.xml with your colors and theme files,
generated with the m3 figma plugin or the m3 theme generator website

Tweak the theme to make the app overlap with the statusbar
https://stackoverflow.com/questions/22192291/how-to-change-the-status-bar-color-in-android
https://stackoverflow.com/a/52437297/9169799

## data binding

in build gradle, inside the android{} scope, add

    buildFeatures {
        dataBinding true
    }

Create a fragment with its own viewmodel, using the navigation ui

modify the fragment view to be like this

    <?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:tools="http://schemas.android.com/tools"
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto">
        <data>
            <variable
                name="viewModel"
                type="com.example....ViewModel" />
        </data>
        <androidx.constraintlayout.widget.ConstraintLayout

        <!-- Fragment design here -->

        </androidx.constraintlayout.widget.ConstraintLayout>
    </layout>

## data layer

Follow this tutorial serie
https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118

In this serie you should only focus on the data layer: room, the repository, and
the way the repository is used in a viewmodel.

A good resource on room is also this page: https://developer.android.com/training/data-storage/room#groovy

Now, there are two important things that the video serie don't cover:
we also want to make requests from a repository, so we need a good way to make
async requests on another thread, and return some sort of promise(javascript term.)

For this, check out this resource:
https://developer.android.com/guide/background/asynchronous/java-threads
Basically, we either return livedata or a callback


Finally, if you followed the video tutorials, you initialized the repository in a
viewmodel. This is fine when the repository is small and only uses room, but since we also need to handle web requests, initializing every time is gonna be an expensive operation. we need to transform the repository into a singleton, and in the viewmodel simly call the getInstance method.


