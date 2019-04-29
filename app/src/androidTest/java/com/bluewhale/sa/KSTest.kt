package com.bluewhale.sa

import android.util.Log
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.bluewhale.sa.ui.MainActivity
import com.bluewhale.sa.ui.shift.work.WorkFragment
import com.bluewhale.sa.util.KS
import org.ethereum.geth.Geth
import org.ethereum.geth.KeyStore
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.web3j.crypto.MnemonicUtils
import java.io.File
import java.security.SecureRandom


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class KSTest {
    @get:Rule var activityTestRule = ActivityTestRule(MainActivity::class.java)

    private var mMainActivity: MainActivity? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mMainActivity = activityTestRule.activity
    }

    @Test
    fun shiftButtonIsEnabled() {
        val f = WorkFragment()

        //mMainActivity?.replaceFragmentInActivity(R.id.contentFrame, f, "WorkFragment")

        val masterPassword = generatePassword()
        var address = generateAddress(masterPassword)
        Log.e("KSTest", "1. address: $address")

        val result =KS.put(mMainActivity!!, address, masterPassword)
        Log.e("KSTest", "put.result: $result")
        assertEquals(result, true)

        Log.e("KSTest", "2. address: $address")

        var storedPassword = ""
        KS.get(mMainActivity!!, address)?.also{
            storedPassword = String(it)
        }

        Log.e("KSTest", "3. storedPassword: $storedPassword")
        //Log.e("KSTest", "3. address: $address")
        //assertEquals(address, string)

        val exportKey = exportKey(address, storedPassword, "!zxcv5231")
        Log.e("KSTest", "exportKey: $exportKey")
    }

    lateinit var keyStore:KeyStore

    fun exportKey(address:String, password:String , newPassword:String):String{
        val account1 = findAccount(address)
        return String(keyStore.exportKey(account1, password, newPassword))
    }

    private fun findAccount(address: String): org.ethereum.geth.Account {
        val accounts = keyStore.getAccounts()
        val len = accounts.size().toInt()
        for (i in 0 until len) {
            try {
                android.util.Log.d("ACCOUNT_FIND", "Address: " + accounts.get(i.toLong()).getAddress().getHex())
                if (accounts.get(i.toLong()).getAddress().getHex().equals(address, ignoreCase = true)) {
                    return accounts.get(i.toLong())
                }
            } catch (ex: Exception) {
                /* Quietly: interest only result, maybe next is ok. */
            }

        }
        throw Exception("Wallet with address: $address not found")
    }

    fun generateAddress(masterPassword:String):String{
        keyStore = makeKeystore()
        return keyStore
            .newAccount(masterPassword)
            .getAddress()
            .getHex()
            .toLowerCase()
    }

    fun generatePassword():String{
        val bytes = ByteArray(256)
        val random = SecureRandom()
        random.nextBytes(bytes)
        return String(bytes)
    }

    fun makeKeystore():KeyStore{
        val keyStoreFile = File(mMainActivity!!.getFilesDir(), "keystore/keystore")
        return KeyStore(keyStoreFile.getAbsolutePath(), Geth.LightScryptN, Geth.LightScryptP)
    }

}
