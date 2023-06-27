# DB_Coding_exercise

Running the main in Runner will print the Market Info for all days of trading and all tickers.


needs to be set. MarketInfo contains all methods to display daily values for open price, close price, highest price, lowest price and daily traded volume for all tickers and the index. These are:

  printDailyMarketInfo(String day)
  Prints the daily values for all tickers.
  
  printTickerMarketInfo(String ticker)
  Prints the daily values for all days for one ticker.
  
  printSpecificMarketInfo(String ticker, String day)
  Prints the daily values of one ticker on one day.
  
  printAllMarketInfo():
  Prints daily values for all days and tickers thatare present in the csv file.

  
  The date format of the print is "dd.MM.yyyy", which is also required for the input-Strings of the above mentioned methods.

If starting from scratch:

Before creating a MarketInfo object, which will create and store a list of all market information, the inputFile needs to be read with the CSVReader and a DateTimeFormat needs to  be set.
After then reading the file with readListOfTrades(), a HashMap to calculate the index ticker needs to be instanciated. Then a new MarketObject can be instanciated,
with the List of Trades and HasHMap as arguments. Then calling createCompleteMarketInfo() on the MarketInfo object will determine the market information for
each ticker and each day. The methods mentioned above can then be used to display this information.

  Input Data:
    Excepts additional tickers and "." separated numbers, as long as there are no additional number separators. (E.g. 30,000.2)
    Date Format can vary in the inputfile, but needs to be specified in the DateTimeFormatter.

    
  
