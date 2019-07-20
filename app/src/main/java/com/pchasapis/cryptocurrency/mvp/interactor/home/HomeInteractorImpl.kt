package com.pchasapis.cryptocurrency.mvp.interactor.home

import com.google.gson.Gson
import com.pchasapis.cryptocurrency.models.common.DataResult
import com.pchasapis.cryptocurrency.models.objects.RateDataModel
import com.pchasapis.cryptocurrency.models.parsers.crypto.list.Crypto
import com.pchasapis.cryptocurrency.models.parsers.crypto.list.CryptoListResponse
import com.pchasapis.cryptocurrency.models.parsers.crypto.liveData.LiveDataResponse
import com.pchasapis.cryptocurrency.models.parsers.crypto.liveData.Rate
import com.pchasapis.cryptocurrency.mvp.interactor.base.BaseInteractor
import com.pchasapis.cryptocurrency.network.client.CryptoClient
import org.json.JSONObject
import timber.log.Timber


class HomeInteractorImpl(private var cryptoClient: CryptoClient) : BaseInteractor(), HomeInteractor {


    override suspend fun onRetrieveCrypotList(): DataResult<List<Crypto>> {
        return try {
            val response = cryptoClient.getCryptoListAsync().await()
            DataResult(handleCryptoData(response))
        } catch (t: Throwable) {
            Timber.d(t)
            DataResult(throwable = t)
        }
    }

    override suspend fun onRetrieveRates(): DataResult< List<RateDataModel>> {
        return try {
            val response = cryptoClient.getLiveRatesAsync().await()
            DataResult(handleLiveData(response))
        } catch (t: Throwable) {
            Timber.d(t)
            DataResult(throwable = t)
        }
    }

    private fun handleLiveData(response: LiveDataResponse): List<RateDataModel> {

        var gson = Gson()
        val jsonObject = JSONObject(gson.toJson(response.liveDataRates))

        val keys = jsonObject.keys()
        val rateList = arrayListOf<RateDataModel>()
        while (keys.hasNext()) {
            val key = keys.next()
            val value = jsonObject.opt(key)

            val rate = gson.fromJson(value.toString(), Rate::class.java)
            rateList.add(RateDataModel(key, rate, response.target ?: ""))
        }
        return rateList
    }


    private fun handleCryptoData(response: CryptoListResponse): List<Crypto> {

        var gson = Gson()
        val jsonObject = JSONObject(gson.toJson(response.cryptoList))

        val keys = jsonObject.keys()
        val cryptoList = arrayListOf<Crypto>()
        while (keys.hasNext()) {
            val key = keys.next()
            val value = jsonObject.opt(key)

            val crypto = gson.fromJson(value.toString(), Crypto::class.java)
            cryptoList.add(crypto)
        }
        return cryptoList
    }

}