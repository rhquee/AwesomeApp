package pl.kfrak.awesomeapp;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import pl.kfrak.awesomeapp.fragments.ExplorerFragment;
import pl.kfrak.awesomeapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements ExplorerFragment.ExploratorInteractionListener {

    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        openExplorerFragment(Environment.getRootDirectory().getPath(), false);

    }

    private void openExplorerFragment(String path, boolean canGoBack) {
        //deklaracja animacji
        int enterAnim = android.R.anim.slide_in_left;
        int exitAnim = android.R.anim.slide_out_right;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //animacja dla fragmentu
        transaction.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim);

        transaction.add(R.id.mainActivity_fragmentContainer, ExplorerFragment.newInstance(path));
 //       transaction.addToBackStack(null);
        if (canGoBack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openSettingsFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSettingsFragment() {
        //zaczynamy akcje, zeby cos sie gdzie spodmienilo
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mainActivity_fragmentContainer, new SettingsFragment());
        //sluzy do tego, zeby moc dawac "wstecz"
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onPathClicked(String newFilePath) {
        openExplorerFragment(newFilePath, true);
    }
}
