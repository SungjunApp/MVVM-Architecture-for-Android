package com.android.app.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import org.bouncycastle.util.encoders.Hex
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runners.Parameterized
import org.mockito.MockitoAnnotations
import org.web3j.crypto.MnemonicUtils
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class MnemonicUtilsTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    /**
     * The initial entropy for the current test vector. This entropy should be used
     * to generate mnemonic and seed.
     */
    private var initialEntropy: ByteArray = Hex.decode("")

    /**
     * Expected mnemonic for the given [.initialEntropy].
     */
    private var mnemonic: String = ""

    /**
     * Expected seed based on the calculated [.mnemonic] and default passphrase.
     */
    private var seed: ByteArray = Hex.decode("")

    @Before
    fun setupViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)
        initialEntropy = Hex.decode("")
        mnemonic = ""
        seed = Hex.decode(seed)
    }

    /**
     * get list pages
     *
     * listSize : 30
     * lastItemId : id(30)
     */
    @Test
    fun getListTest() {
        val json = getJson()
        for (texts in json.english) {
            val initialEntropy = Hex.decode(texts[0])
            val genMnemonic = MnemonicUtils.generateMnemonic(initialEntropy)
            println("==================")
            //println("mnemonic.json: ${texts[1]}")
            //println("mnemonic.created: ${genMnemonic}")

            val genEntropy = MnemonicUtils.generateEntropy(texts[1])
            //println("entropy.json: ${Arrays.toString(initialEntropy)}")
            //println("entropy.created: ${Arrays.toString(genEntropy) }")

            val genSeed = MnemonicUtils.generateSeed(texts[1], "TREZOR")
            println("seed.json: ${texts[2]}")
            println("seed.created: ${Arrays.toString(genSeed)}")

            /*for ((i, text )in texts.withIndex()) {
                try {
                    val initialEntropy = Hex.decode(text)

                    val actualMnemonic = MnemonicUtils.generateMnemonic(Hex.decode(text))
                    println("$i, mnemonic: ${actualMnemonic}")
                } catch (e: Exception) {
                }

                try {
                    val Entropy = MnemonicUtils.generateEntropy(text)
                    println("$i, Entropy: ${Entropy}")
                } catch (e: Exception) {
                }

                try {
                    val seed = MnemonicUtils.generateSeed(text, "TREZOR")
                    println("$i, seed: ${seed}")
                } catch (e: Exception) {
                }
            }*/
        }


        println(json.toString())
    }

    class TestJson constructor(val english: ArrayList<ArrayList<String>>) {
        override fun toString(): String {
            val sb = StringBuilder()
            for (texts in english) {
                for (text in texts) {
                    sb.append(text)
                }
            }
            return sb.toString()
        }
    }

    fun getJson(): TestJson {
        val sb = StringBuilder()
        val collections = data()
        println("test hong.collections: ${collections.size}")
        for (data in collections) {
            println("test hong data.size: ${data.size}")
            for (line in data) {
                //        println("line: ${line.toString()}")
                val string = line.toString().trim()
                sb.append(string)
            }
        }
        //println(sb.toString())
        val gson = Gson()
        return gson.fromJson<TestJson>(sb.toString(), TestJson::class.java!!)
        //return JSONObject(sb.toString())
    }

    private val SAMPLE_FILE = "test.json"
    private fun getFileFromPath(fileName: String): Path {
        val resource: URL = javaClass.classLoader.getResource(fileName)
        return Paths.get(resource.toURI())
    }


    /**
     * Loads the test vectors into a in-memory list and feed them one after another to
     * our parameterized tests.
     *
     * @return Collection of test vectors in which each vector is an array containing
     * initial entropy, expected mnemonic and expected seed.
     * @throws IOException Shouldn't happen!
     */
    @Parameterized.Parameters
    @Throws(IOException::class)
    fun data(): Collection<Array<Any>> {
        val data = Files.lines(getFileFromPath(SAMPLE_FILE)).collect(Collectors.joining("\n"))
        val each = data.split("###".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

        val parameters = ArrayList<Array<Any>>()
        for (part in each) {
            parameters.add(part.trim({ it <= ' ' }).split("\n".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray())
        }

        return parameters
    }


    @Test
    fun generateMnemonicShouldGenerateExpectedMnemonicWords() {
        val actualMnemonic = MnemonicUtils.generateMnemonic(initialEntropy)

        Assert.assertEquals(mnemonic, actualMnemonic)
    }

    @Test
    fun generateSeedShouldGenerateExpectedSeeds() {
        val actualSeed = MnemonicUtils.generateSeed(mnemonic, "TREZOR")

        Assert.assertArrayEquals(seed, actualSeed)
    }

    @Test
    fun generateEntropyShouldGenerateExpectedEntropy() {
        val actualEntropy = MnemonicUtils.generateEntropy(mnemonic)

        Assert.assertArrayEquals(initialEntropy, actualEntropy)
    }
}