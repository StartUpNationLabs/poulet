/* tslint:disable */
/* eslint-disable */
/**
 * analyse-haut-niveau-management API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import type { Configuration } from '../configuration';
import type { AxiosPromise, AxiosInstance, RawAxiosRequestConfig } from 'axios';
import globalAxios from 'axios';
// Some imports not used depending on template conditions
// @ts-ignore
import { DUMMY_BASE_URL, assertParamExists, setApiKeyToObject, setBasicAuthToObject, setBearerAuthToObject, setOAuthToObject, setSearchParams, serializeDataIfNeeded, toPathString, createRequestFunction } from '../common';
// @ts-ignore
import { BASE_PATH, COLLECTION_FORMATS, type RequestArgs, BaseAPI, RequiredError, operationServerMap } from '../base';
// @ts-ignore
import type { JsonObjectInner } from '../models';
/**
 * MetricsResourceApi - axios parameter creator
 * @export
 */


export const MetricsResourceApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @param {string} [end] 
         * @param {string} [gatewayId] 
         * @param {string} [start] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        metricsKpisGet: async (end?: string, gatewayId?: string, start?: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            const localVarPath = `/metrics/kpis`;
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            if (end !== undefined) {
                localVarQueryParameter['end'] = end;
            }

            if (gatewayId !== undefined) {
                localVarQueryParameter['gatewayId'] = gatewayId;
            }

            if (start !== undefined) {
                localVarQueryParameter['start'] = start;
            }


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {string} [gatewayId] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        metricsTodayGet: async (gatewayId?: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            const localVarPath = `/metrics/today`;
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            if (gatewayId !== undefined) {
                localVarQueryParameter['gatewayId'] = gatewayId;
            }


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
    }
};

/**
 * MetricsResourceApi - functional programming interface
 * @export
 */
export const MetricsResourceApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = MetricsResourceApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @param {string} [end] 
         * @param {string} [gatewayId] 
         * @param {string} [start] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async metricsKpisGet(end?: string, gatewayId?: string, start?: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<JsonObjectInner>>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.metricsKpisGet(end, gatewayId, start, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['MetricsResourceApi.metricsKpisGet']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} [gatewayId] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async metricsTodayGet(gatewayId?: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<JsonObjectInner>>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.metricsTodayGet(gatewayId, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['MetricsResourceApi.metricsTodayGet']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * MetricsResourceApi - factory interface
 * @export
 */
export const MetricsResourceApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = MetricsResourceApiFp(configuration)
    return {
        /**
         * 
         * @param {MetricsResourceApiMetricsKpisGetRequest} requestParameters Request parameters.
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        metricsKpisGet(requestParameters: MetricsResourceApiMetricsKpisGetRequest = {}, options?: RawAxiosRequestConfig): AxiosPromise<Array<JsonObjectInner>> {
            return localVarFp.metricsKpisGet(requestParameters.end, requestParameters.gatewayId, requestParameters.start, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {MetricsResourceApiMetricsTodayGetRequest} requestParameters Request parameters.
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        metricsTodayGet(requestParameters: MetricsResourceApiMetricsTodayGetRequest = {}, options?: RawAxiosRequestConfig): AxiosPromise<Array<JsonObjectInner>> {
            return localVarFp.metricsTodayGet(requestParameters.gatewayId, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * Request parameters for metricsKpisGet operation in MetricsResourceApi.
 * @export
 * @interface MetricsResourceApiMetricsKpisGetRequest
 */
export interface MetricsResourceApiMetricsKpisGetRequest {
    /**
     * 
     * @type {string}
     * @memberof MetricsResourceApiMetricsKpisGet
     */
    readonly end?: string

    /**
     * 
     * @type {string}
     * @memberof MetricsResourceApiMetricsKpisGet
     */
    readonly gatewayId?: string

    /**
     * 
     * @type {string}
     * @memberof MetricsResourceApiMetricsKpisGet
     */
    readonly start?: string
}

/**
 * Request parameters for metricsTodayGet operation in MetricsResourceApi.
 * @export
 * @interface MetricsResourceApiMetricsTodayGetRequest
 */
export interface MetricsResourceApiMetricsTodayGetRequest {
    /**
     * 
     * @type {string}
     * @memberof MetricsResourceApiMetricsTodayGet
     */
    readonly gatewayId?: string
}

/**
 * MetricsResourceApi - object-oriented interface
 * @export
 * @class MetricsResourceApi
 * @extends {BaseAPI}
 */
export class MetricsResourceApi extends BaseAPI {
    /**
     * 
     * @param {MetricsResourceApiMetricsKpisGetRequest} requestParameters Request parameters.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof MetricsResourceApi
     */
    public metricsKpisGet(requestParameters: MetricsResourceApiMetricsKpisGetRequest = {}, options?: RawAxiosRequestConfig) {
        return MetricsResourceApiFp(this.configuration).metricsKpisGet(requestParameters.end, requestParameters.gatewayId, requestParameters.start, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {MetricsResourceApiMetricsTodayGetRequest} requestParameters Request parameters.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof MetricsResourceApi
     */
    public metricsTodayGet(requestParameters: MetricsResourceApiMetricsTodayGetRequest = {}, options?: RawAxiosRequestConfig) {
        return MetricsResourceApiFp(this.configuration).metricsTodayGet(requestParameters.gatewayId, options).then((request) => request(this.axios, this.basePath));
    }
}

