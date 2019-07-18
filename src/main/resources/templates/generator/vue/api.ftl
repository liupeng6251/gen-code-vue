import axios from 'axios';

let base = '<#if (classInfo.businessModule)?? && classInfo.businessModule gt 0>/${classInfo.businessModule}</#if>';

export const ${classInfo.name?uncap_first}PageList = params => { return axios.post(`${r'${'}base}/${classInfo.name?uncap_first}/pageList.do`, { params: params }); };

export const ${classInfo.name?uncap_first}Detete = params => { return axios.get(`${r'${'}base}/${classInfo.name?uncap_first}/delete.do`, { params: params }); };

export const ${classInfo.name?uncap_first}BatchDelete = params => { return axios.get(`${r'${'}base}/${classInfo.name?uncap_first}/batchDelete.do`, { params: params }); };

export const ${classInfo.name?uncap_first}Update = params => { return axios.post(`${r'${'}base}/${classInfo.name?uncap_first}/update.do`, { params: params }); };

export const ${classInfo.name?uncap_first}Detail = params => { return axios.get(`${r'${'}base}/${classInfo.name?uncap_first}/detail.do`, { params: params }); };

export const ${classInfo.name?uncap_first}Save = params => { return axios.post(`${r'${'}base}/${classInfo.name?uncap_first}/save.do`, { params: params }); };