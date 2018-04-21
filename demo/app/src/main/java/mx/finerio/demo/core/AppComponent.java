package mx.finerio.demo.core;

import javax.inject.Singleton;

import dagger.Component;
import mx.finerio.demo.ui.HomeBankActivity;
import mx.finerio.demo.ui.LoginActivity;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {

    void inject(HomeBankActivity main);
    void inject(LoginActivity main);
}