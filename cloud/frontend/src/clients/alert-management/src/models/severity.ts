/* tslint:disable */
/* eslint-disable */
/**
 * alert-management API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */



/**
 * 
 * @export
 * @enum {string}
 */

export const Severity = {
    Low: 'LOW',
    Medium: 'MEDIUM',
    Info: 'INFO',
    Warning: 'WARNING',
    Critical: 'CRITICAL'
} as const;

export type Severity = typeof Severity[keyof typeof Severity];


