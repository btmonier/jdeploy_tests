package net.maizegenetics.analysis.association

import org.junit.Test
import kotlin.random.Random

class ManovaPluginTest {

    @Test
    fun imputeNTest() {
        val genotype = arrayOf("A","A","A","A","A","A","T","T","N","N","N","N","N","N","N","N")
        println("${genotype.joinToString(",")}")

        println("${imputeNsInGenotype(genotype, Random(0)).joinToString(",")}")
    }
}

