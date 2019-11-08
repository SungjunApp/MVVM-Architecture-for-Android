package com.sjsoft.app.di

import com.amazonaws.ClientConfiguration
import com.amazonaws.Protocol
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.sjsoft.app.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AWSModule {
    @Provides
    @Singleton
    fun provideBasicAWSCredentials(): AWSCredentials {
        return BasicAWSCredentials(BuildConfig.AWS_ACCESS_KEY, BuildConfig.AWS_SECRET_KEY)
    }

    @Provides
    @Singleton
    fun provideAmazonS3(awsCreds: AWSCredentials): AmazonS3 {
        val clientConfig = ClientConfiguration()
        clientConfig.protocol = Protocol.HTTP
        val conn = AmazonS3Client(awsCreds, clientConfig)
        conn.setRegion(Region.getRegion(BuildConfig.AWS_REGION))
        return conn
    }

//    /** us-gov-east-1.  */
//    US_GOV_EAST_1("us-gov-east-1"),
//
//    /** us-east-1.  */
//    US_EAST_1("us-east-1"),
//
//    /** us-east-2.  */
//    US_EAST_2("us-east-2"),
//
//    /** eu-west-1/  */
//    EU_WEST_1("eu-west-1"),
//
//    /** eu-west-2.  */
//    EU_WEST_2("eu-west-2"),
//
//    /** eu-west-3.  */
//    EU_WEST_3("eu-west-3"),
//
//    /** eu-central-1.  */
//    EU_CENTRAL_1("eu-central-1"),
//
//    /** eu-north-1.  */
//    EU_NORTH_1("eu-north-1"),
//
//    /** ap-east-1.  */
//    AP_EAST_1("ap-eastf-1"),
//
//    /** ap-south-1.  */
//    AP_SOUTH_1("ap-south-1"),
//
//    /** ap-southeast-1.  */
//    AP_SOUTHEAST_1("ap-southeast-1"),
//
//    /** ap-southeast-2.  */
//    AP_SOUTHEAST_2("ap-southeast-2"),
//
//    /** sa-east-1.  */
//    SA_EAST_1("sa-east-1"),
//
//    /** ca-central-1.  */
//    CA_CENTRAL_1("ca-central-1"),
//
//    /** cn-north-1.  */
//    CN_NORTH_1("cn-north-1"),
//
//    /** cn-northwest-1.  */
//    CN_NORTHWEST_1("cn-northwest-1"),
//
//    /** me-south-1.  */
//    ME_SOUTH_1("me-south-1");
}