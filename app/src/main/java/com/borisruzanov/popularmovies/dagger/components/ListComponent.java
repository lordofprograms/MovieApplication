package com.borisruzanov.popularmovies.dagger.components;

import com.borisruzanov.popularmovies.dagger.modules.ListModule;
import com.borisruzanov.popularmovies.dagger.scopes.ListScope;
import com.borisruzanov.popularmovies.presentation.list.ListPresenter;

import dagger.Component;

@ListScope
@Component(modules = ListModule.class, dependencies = AppComponent.class )
public interface ListComponent {

    void inject(ListPresenter presenter);

}
