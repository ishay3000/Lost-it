package com.example.ishaycena.tabfragments.Utilities;

public interface IDataFetcher {
    /**
     * fetches data from firebase
     */
    void FetchData();

    /**
     * event triggers when data is fetched using FetchData function
     */
    void OnDataFetched();
}
