package com.sjsoft.app.di

import android.content.Context
import com.amazonaws.auth.BasicAWSCredentials
import com.pixlee.pixleesdk.*
import com.sjsoft.app.BuildConfig
import com.sjsoft.app.constant.AppConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.sun.tools.doclets.formats.html.resources.standard
import com.amazonaws.services.s3.AmazonS3



@Module
class AWSModule {
    @Provides
    @Singleton
    fun provideBasicAWSCredentials(context: Context): BasicAWSCredentials {
        return BasicAWSCredentials(BuildConfig.AWS_ACCESS_KEY, BuildConfig.AWS_SECRET_KEY)
    }

    @Provides
    @Singleton
    fun provideAmazonS3(context: Context): AmazonS3 {
        val s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(awsCreds))
            .build()

    }
}