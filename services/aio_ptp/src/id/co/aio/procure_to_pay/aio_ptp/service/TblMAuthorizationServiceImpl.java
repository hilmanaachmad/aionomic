/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.wavemaker.commons.InvalidInputException;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.data.annotations.EntityService;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import id.co.aio.procure_to_pay.aio_ptp.RelDelAuth;
import id.co.aio.procure_to_pay.aio_ptp.RelRoleAuth;
import id.co.aio.procure_to_pay.aio_ptp.TblMAuthorization;


/**
 * ServiceImpl object for domain model class TblMAuthorization.
 *
 * @see TblMAuthorization
 */
@Service("aio_ptp.TblMAuthorizationService")
@Validated
@EntityService(entityClass = TblMAuthorization.class, serviceId = "aio_ptp")
public class TblMAuthorizationServiceImpl implements TblMAuthorizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMAuthorizationServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("aio_ptp.RelRoleAuthService")
    private RelRoleAuthService relRoleAuthService;

    @Lazy
    @Autowired
    @Qualifier("aio_ptp.RelDelAuthService")
    private RelDelAuthService relDelAuthService;

    @Autowired
    @Qualifier("aio_ptp.TblMAuthorizationDao")
    private WMGenericDao<TblMAuthorization, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblMAuthorization, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMAuthorization create(TblMAuthorization tblMauthorization) {
        LOGGER.debug("Creating a new TblMAuthorization with information: {}", tblMauthorization);

        TblMAuthorization tblMauthorizationCreated = this.wmGenericDao.create(tblMauthorization);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblMauthorizationCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMAuthorization getById(Integer tblmauthorizationId) {
        LOGGER.debug("Finding TblMAuthorization by id: {}", tblmauthorizationId);
        return this.wmGenericDao.findById(tblmauthorizationId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMAuthorization findById(Integer tblmauthorizationId) {
        LOGGER.debug("Finding TblMAuthorization by id: {}", tblmauthorizationId);
        try {
            return this.wmGenericDao.findById(tblmauthorizationId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblMAuthorization found with id: {}", tblmauthorizationId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblMAuthorization> findByMultipleIds(List<Integer> tblmauthorizationIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblMAuthorizations by ids: {}", tblmauthorizationIds);

        return this.wmGenericDao.findByMultipleIds(tblmauthorizationIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblMAuthorization update(TblMAuthorization tblMauthorization) {
        LOGGER.debug("Updating TblMAuthorization with information: {}", tblMauthorization);

        this.wmGenericDao.update(tblMauthorization);
        this.wmGenericDao.refresh(tblMauthorization);

        return tblMauthorization;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMAuthorization partialUpdate(Integer tblmauthorizationId, Map<String, Object>tblMauthorizationPatch) {
        LOGGER.debug("Partially Updating the TblMAuthorization with id: {}", tblmauthorizationId);

        TblMAuthorization tblMauthorization = getById(tblmauthorizationId);

        try {
            ObjectReader tblMauthorizationReader = this.objectMapper.reader().forType(TblMAuthorization.class).withValueToUpdate(tblMauthorization);
            tblMauthorization = tblMauthorizationReader.readValue(this.objectMapper.writeValueAsString(tblMauthorizationPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblMauthorizationPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblMauthorization = update(tblMauthorization);

        return tblMauthorization;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMAuthorization delete(Integer tblmauthorizationId) {
        LOGGER.debug("Deleting TblMAuthorization with id: {}", tblmauthorizationId);
        TblMAuthorization deleted = this.wmGenericDao.findById(tblmauthorizationId);
        if (deleted == null) {
            LOGGER.debug("No TblMAuthorization found with id: {}", tblmauthorizationId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblMAuthorization.class.getSimpleName(), tblmauthorizationId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblMAuthorization tblMauthorization) {
        LOGGER.debug("Deleting TblMAuthorization with {}", tblMauthorization);
        this.wmGenericDao.delete(tblMauthorization);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMAuthorization> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblMAuthorizations");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMAuthorization> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblMAuthorizations");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMAuthorization to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMAuthorization to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<RelDelAuth> findAssociatedRelDelAuths(Integer authId, Pageable pageable) {
        LOGGER.debug("Fetching all associated relDelAuths");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("tblMauthorization.authId = '" + authId + "'");

        return relDelAuthService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<RelRoleAuth> findAssociatedRelRoleAuths(Integer authId, Pageable pageable) {
        LOGGER.debug("Fetching all associated relRoleAuths");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("tblMauthorization.authId = '" + authId + "'");

        return relRoleAuthService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service RelRoleAuthService instance
     */
    protected void setRelRoleAuthService(RelRoleAuthService service) {
        this.relRoleAuthService = service;
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service RelDelAuthService instance
     */
    protected void setRelDelAuthService(RelDelAuthService service) {
        this.relDelAuthService = service;
    }

}