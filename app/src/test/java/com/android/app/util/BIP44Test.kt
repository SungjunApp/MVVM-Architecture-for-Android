package com.android.app.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.app.SampleKeys
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.web3j.crypto.Bip32ECKeyPair
import org.web3j.crypto.Bip44WalletUtils
import org.web3j.crypto.Credentials
import org.web3j.crypto.MnemonicUtils
import java.io.File
import java.nio.file.Files


class BIP44Test {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    var tempDir: File? = null

    @Before
    fun setupViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)
        tempDir = createTempDir()
    }

    @Throws(Exception::class)
    fun createTempDir(): File {
        return Files.createTempDirectory(
            BIP44Test::class.java!!.getSimpleName() + "-testkeys"
        ).toFile()
    }


    @Test
    fun testBIP44() {

        //val wallet = Bip44WalletUtils.generateBip44Wallet(SampleKeys.PASSWORD, tempDir)
        //val seed = MnemonicUtils.generateSeed(wallet.mnemonic, SampleKeys.PASSWORD)


        val mnemonic = "assist renew cherry now medal exhaust skin hover scissors few pulse island"
        val seed = MnemonicUtils.generateSeed(mnemonic, SampleKeys.PASSWORD)
        val masterKeypair = Bip32ECKeyPair.generateKeyPair(seed)
        val bip44Keypair = Bip44WalletUtils.generateBip44KeyPair(masterKeypair)
        val credentials = Credentials.create(bip44Keypair)

        val loadedCredentials = Bip44WalletUtils.loadBip44Credentials(SampleKeys.PASSWORD, mnemonic)

        //println("wallet: $wallet")
        println("wallet.mnemonic: ${mnemonic}")
        println("SampleKeys.PASSWORD: ${SampleKeys.PASSWORD}")
        println("seed: $seed")
        println("masterKeypair: $masterKeypair")
        println("bip44Keypair: $bip44Keypair")
        println("credentials: ${credentials.address}")
        println("credentials.privateKey: ${credentials.ecKeyPair.privateKey}")
        println("credentials.publicKey: ${credentials.ecKeyPair.publicKey}")
        println("loadedCredentials: ${loadedCredentials.address}")
        println("loadedCredentials.privateKey: ${loadedCredentials.ecKeyPair.privateKey}")
        println("loadedCredentials.publicKey: ${loadedCredentials.ecKeyPair.publicKey}")

        assertEquals(
            credentials,
            loadedCredentials
        )
    }
}