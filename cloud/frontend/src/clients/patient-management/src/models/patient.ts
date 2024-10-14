/* tslint:disable */
/* eslint-disable */
/**
 * patient-management API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


// May contain unused imports in some cases
// @ts-ignore
import type { Gender } from './gender';

/**
 * 
 * @export
 * @interface Patient
 */
export interface Patient {
    /**
     * 
     * @type {string}
     * @memberof Patient
     */
    'firstname'?: string;
    /**
     * 
     * @type {string}
     * @memberof Patient
     */
    'lastname'?: string;
    /**
     * 
     * @type {Gender}
     * @memberof Patient
     */
    'gender'?: Gender;
    /**
     * Gateway ID as a 24-character hexadecimal string
     * @type {string}
     * @memberof Patient
     */
    'gatewayId'?: string;
    /**
     * Patient ID as a 24-character hexadecimal string
     * @type {string}
     * @memberof Patient
     */
    'id'?: string;
}



