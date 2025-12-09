using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace APIVerve.API.CurrencySymbols
{
    /// <summary>
    /// Query options for the Currency Symbols API
    /// </summary>
    public class CurrencySymbolsQueryOptions
    {
        /// <summary>
        /// The currency code for which you want to get the symbol (e.g., USD)
        /// Example: USD
        /// </summary>
        [JsonProperty("currency")]
        public string Currency { get; set; }
    }
}
