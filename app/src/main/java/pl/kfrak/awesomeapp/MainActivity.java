package pl.kfrak.awesomeapp;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import pl.kfrak.awesomeapp.fragments.ExplorerFragment;
import pl.kfrak.awesomeapp.fragments.OpenFileFragment;
import pl.kfrak.awesomeapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements ExplorerFragment.ExploratorInteractionListener, OpenFileFragment.InteractionListener {

    private MenuItem item;

    private static final int ENTER_ANIM = android.R.anim.slide_in_left;
    private static final int EXIT_ANIM = android.R.anim.slide_out_right;

    private static final int EXTERNAL_STORAGE_REQUEST_CODE = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        openExplorerFragment(Environment.getRootDirectory().getPath(), false);

    }

    private void openExplorerFragment(String path, boolean canGoBack) {

        Fragment fragment = ExplorerFragment.newInstance(path);
        openFragment(fragment, canGoBack);
    }



    private void openFragment(Fragment fragment, boolean canGoBack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        //animacja dla fragmentu
        transaction.setCustomAnimations(ENTER_ANIM, EXIT_ANIM, ENTER_ANIM, EXIT_ANIM);

                //       transaction.addToBackStack(null);
                if (canGoBack){
            transaction.add(R.id.mainActivity_fragmentContainer, fragment );
            transaction.addToBackStack(null);
        } else
            transaction.replace(R.id.mainActivity_fragmentContainer, fragment);
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettingsFragment();
                return true;

            case R.id.action_gotoexternal:
                openExplorerFragment(Environment.getExternalStorageDirectory().getPath(), false);
                return true;

            case R.id.action_gotoroot:
                openExplorerFragment(Environment.getRootDirectory().getPath(), false);
                return true;
            //  int id = item.getItemId();

            //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            openSettingsFragment();
//            return true;
//        }


        }
            return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openExplorerFragment(Environment.getExternalStorageDirectory().getPath(), false);
                else
                    Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show();
                return;
            }
        }
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
    public void onDirectoryClicked(String newPath) {
        openExplorerFragment(newPath, true);
    }

    @Override
    public void onFileClicked(String filePath) {
        openFragment(OpenFileFragment.newInstance(filePath), true);
    }

    @Override
    public void onBackClicked() {
        //funckja do cofania
        //jesli mamy fragmenty i bedziemy sie cofac to beda sie cofac w kolejnosci ich otwierania
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void doNothing() {
        //
    }
}
