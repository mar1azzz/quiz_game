package com.example.quiz_game;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Получаем NavController
        //NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.nav_host_fragment);
        //assert navHostFragment != null;
        //NavController navController = navHostFragment.getNavController();


        // Указываем, какие фрагменты считаются верхнего уровня (без кнопки "Назад")
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.authFragment,
                R.id.menuFragment)
                .build();

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
