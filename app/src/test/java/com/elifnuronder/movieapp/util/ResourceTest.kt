package com.elifnuronder.movieapp.util

import org.junit.Assert.*
import org.junit.Test

class ResourceTest {

    @Test
    fun `Resource Success creates correct instance`() {
        // Given
        val testData = "test data"

        // When
        val resource = Resource.Success(testData)

        // Then
        assertEquals(testData, resource.data)
        assertNull(resource.message)
    }

    @Test
    fun `Resource Error creates correct instance`() {
        // Given
        val errorMessage = "Something went wrong"

        // When
        val resource = Resource.Error<String>(errorMessage)

        // Then
        assertNull(resource.data)
        assertEquals(errorMessage, resource.message)
    }
}
