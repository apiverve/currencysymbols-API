declare module '@apiverve/currencysymbols' {
  export interface currencysymbolsOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface currencysymbolsResponse {
    status: string;
    error: string | null;
    data: any;
    code?: number;
  }

  export default class currencysymbolsWrapper {
    constructor(options: currencysymbolsOptions);

    execute(callback: (error: any, data: currencysymbolsResponse | null) => void): Promise<currencysymbolsResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: currencysymbolsResponse | null) => void): Promise<currencysymbolsResponse>;
    execute(query?: Record<string, any>): Promise<currencysymbolsResponse>;
  }
}
