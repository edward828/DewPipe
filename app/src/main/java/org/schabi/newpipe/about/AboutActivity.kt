package org.schabi.newpipe.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import org.schabi.newpipe.BuildConfig
import org.schabi.newpipe.R
import org.schabi.newpipe.util.NavigationHelper
import org.schabi.newpipe.util.ThemeHelper

class AboutActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setTheme(this)

        setContentView(R.layout.activity_about)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container)
        mViewPager!!.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(mViewPager)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_about, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_settings -> {
                NavigationHelper.openSettings(this)
                return true
            }
            R.id.action_show_downloads -> return NavigationHelper.openDownloads(this)
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class AboutFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_about, container, false)
            val context = this.context

            val version = rootView.findViewById<TextView>(R.id.app_version)
            version.text = BuildConfig.VERSION_NAME

            val githubLink = rootView.findViewById<View>(R.id.github_link)
            githubLink.setOnClickListener { nv -> openWebsite(context!!.getString(R.string.github_url), context) }

            val donationLink = rootView.findViewById<View>(R.id.donation_link)
            donationLink.setOnClickListener { v -> openWebsite(context!!.getString(R.string.donation_url), context) }

            val websiteLink = rootView.findViewById<View>(R.id.website_link)
            websiteLink.setOnClickListener { nv -> openWebsite(context!!.getString(R.string.website_url), context) }

            val privacyPolicyLink = rootView.findViewById<View>(R.id.privacy_policy_link)
            privacyPolicyLink.setOnClickListener { v -> openWebsite(context!!.getString(R.string.privacy_policy_url), context) }

            return rootView
        }

        private fun openWebsite(url: String, context: Context) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }

        companion object {
            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(): AboutFragment {
                return AboutFragment()
            }
        }
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return AboutFragment.newInstance()
                1 -> return LicenseFragment.newInstance(SOFTWARE_COMPONENTS)
            }
            return null
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return getString(R.string.tab_about)
                1 -> return getString(R.string.tab_licenses)
            }
            return null
        }
    }

    companion object {

        /**
         * List of all software components
         */
        private val SOFTWARE_COMPONENTS = arrayOf(
                SoftwareComponent(
                        "Giga Get",
                        "2014",
                        "Peter Cai",
                        "https://github.com/PaperAirplane-Dev-Team/GigaGet",
                        StandardLicenses.GPL2),

                SoftwareComponent("NewPipe Extractor",
                        "2017",
                        "Christian Schabesberger",
                        "https://github.com/TeamNewPipe/NewPipeExtractor",
                        StandardLicenses.GPL3),

                SoftwareComponent("Jsoup",
                        "2017",
                        "Jonathan Hedley",
                        "https://github.com/jhy/jsoup",
                        StandardLicenses.MIT),

                SoftwareComponent("Rhino",
                        "2015",
                        "Mozilla",
                        "https://www.mozilla.org/rhino/",
                        StandardLicenses.MPL2),

                SoftwareComponent("ACRA",
                        "2013",
                        "Kevin Gaudin",
                        "http://www.acra.ch",
                        StandardLicenses.APACHE2),

                SoftwareComponent("Universal Image Loader",
                        "2011 - 2015",
                        "Sergey Tarasevich",
                        "https://github.com/nostra13/Android-Universal-Image-Loader",
                        StandardLicenses.APACHE2),

                SoftwareComponent("CircleImageView",
                        "2014 - 2017",
                        "Henning Dodenhof",
                        "https://github.com/hdodenhof/CircleImageView",
                        StandardLicenses.APACHE2),

                SoftwareComponent("ParalaxScrollView",
                        "2014",
                        "Nir Hartmann",
                        "https://github.com/nirhart/ParallaxScroll",
                        StandardLicenses.MIT),

                SoftwareComponent("NoNonsense-FilePicker",
                        "2016",
                        "Jonas Kalderstam",
                        "https://github.com/spacecowboy/NoNonsense-FilePicker",
                        StandardLicenses.MPL2),

                SoftwareComponent("ExoPlayer",
                        "2014-2017",
                        "Google Inc",
                        "https://github.com/google/ExoPlayer",
                        StandardLicenses.APACHE2),

                SoftwareComponent("RxAndroid",
                        "2015",
                        "The RxAndroid authors",
                        "https://github.com/ReactiveX/RxAndroid",
                        StandardLicenses.APACHE2),

                SoftwareComponent("RxJava",
                        "2016-present",
                        "RxJava Contributors",
                        "https://github.com/ReactiveX/RxJava",
                        StandardLicenses.APACHE2),

                SoftwareComponent("RxBinding",
                        "2015",
                        "Jake Wharton",
                        "https://github.com/JakeWharton/RxBinding",
                        StandardLicenses.APACHE2)
        )
    }
}
