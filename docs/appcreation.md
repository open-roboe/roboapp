
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


## design with xml

https://constraintlayout.com/basics/create_chains.html



