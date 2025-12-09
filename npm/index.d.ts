declare module '@apiverve/currencysymbols' {
  export interface currencysymbolsOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface currencysymbolsResponse {
    status: string;
    error: string | null;
    data: CurrencySymbolsData;
    code?: number;
  }


  interface CurrencySymbolsData {
      countriesFound: CountriesFound[];
      currency:       Currency;
  }
  
  interface CountriesFound {
      countryName:     string;
      currencyName:    CurrencyName;
      currencyISOCode: Currency;
      currencyNumber:  string;
      currencyMnrUnts: string;
      currencySymbol:  CurrencySymbol;
  }
  
  enum Currency {
      Usd = "USD",
  }
  
  enum CurrencyName {
      USDollar = "US Dollar",
  }
  
  enum CurrencySymbol {
      Empty = "$",
  }

  export default class currencysymbolsWrapper {
    constructor(options: currencysymbolsOptions);

    execute(callback: (error: any, data: currencysymbolsResponse | null) => void): Promise<currencysymbolsResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: currencysymbolsResponse | null) => void): Promise<currencysymbolsResponse>;
    execute(query?: Record<string, any>): Promise<currencysymbolsResponse>;
  }
}
