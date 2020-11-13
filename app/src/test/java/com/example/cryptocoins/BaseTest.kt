package com.example.cryptocoins

import com.example.cryptocoins.extensions.InstantExecutorExtension
import com.example.cryptocoins.extensions.RxSchedulerExtension
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.RegisterExtension

abstract class BaseTest {

    @JvmField
    @RegisterExtension
    val rxSchedulerExtension = RxSchedulerExtension()

    @JvmField
    @RegisterExtension
    val instantExecutorExtension = InstantExecutorExtension()

    @BeforeEach
    open fun setUp() {
        MockKAnnotations.init(this)
    }

    @AfterEach
    open fun tearDown() {
        clearAllMocks()
    }
}