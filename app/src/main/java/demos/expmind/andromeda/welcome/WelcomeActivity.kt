package demos.expmind.andromeda.welcome

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentPagerAdapter
import demos.expmind.andromeda.R
import demos.expmind.andromeda.data.YoutubeChannels
import demos.expmind.andromeda.search.SearchActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var categoriesPager: CategoriesPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        categoriesPager = CategoriesPagerAdapter(supportFragmentManager)
        // Set up the ViewPager with the sections adapter.
        viewPager.adapter = categoriesPager
        // Set up tabs
        categoriesTabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_welcome, menu)
        //Setup search action view listener
        val searchItem = menu.findItem(R.id.action_search)
        val searchActionView = searchItem.actionView as SearchView
        searchActionView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(submit: String?): Boolean {
                val searchIntent = Intent(this@WelcomeActivity, SearchActivity::class.java)
                searchIntent.putExtras(Bundle().also { it.putString(SearchActivity.EXTRA_QUERY, submit) })
                startActivity(searchIntent)
                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_search) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * A [FragmentPagerAdapter] that holds video categories and returns a {@link VideoListFragment}
     * corresponding to one of the sections/tabs/pages.
     */
    class CategoriesPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {
        // For now, topics for our demo are hardcoded
        companion object {
            val CATEGORIES: List<YoutubeChannels> = YoutubeChannels.values().asList()
        }

        override fun getItem(position: Int): androidx.fragment.app.Fragment {
            return VideoListFragment.newInstance(CATEGORIES[position].name)
        }

        override fun getPageTitle(position: Int): CharSequence? = CATEGORIES[position].displayName


        override fun getCount(): Int = CATEGORIES.size

    }

}
