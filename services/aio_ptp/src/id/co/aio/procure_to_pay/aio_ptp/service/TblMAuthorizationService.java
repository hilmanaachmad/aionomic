/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
 * Service object for domain model class {@link TblMAuthorization}.
 */
public interface TblMAuthorizationService {

    /**
     * Creates a new TblMAuthorization. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblMAuthorization if any.
     *
     * @param tblMauthorization Details of the TblMAuthorization to be created; value cannot be null.
     * @return The newly created TblMAuthorization.
     */
    TblMAuthorization create(@Valid TblMAuthorization tblMauthorization);


	/**
     * Returns TblMAuthorization by given id if exists.
     *
     * @param tblmauthorizationId The id of the TblMAuthorization to get; value cannot be null.
     * @return TblMAuthorization associated with the given tblmauthorizationId.
	 * @throws EntityNotFoundException If no TblMAuthorization is found.
     */
    TblMAuthorization getById(Integer tblmauthorizationId);

    /**
     * Find and return the TblMAuthorization by given id if exists, returns null otherwise.
     *
     * @param tblmauthorizationId The id of the TblMAuthorization to get; value cannot be null.
     * @return TblMAuthorization associated with the given tblmauthorizationId.
     */
    TblMAuthorization findById(Integer tblmauthorizationId);

	/**
     * Find and return the list of TblMAuthorizations by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tblmauthorizationIds The id's of the TblMAuthorization to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TblMAuthorizations associated with the given tblmauthorizationIds.
     */
    List<TblMAuthorization> findByMultipleIds(List<Integer> tblmauthorizationIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TblMAuthorization. It replaces all fields of the existing TblMAuthorization with the given tblMauthorization.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblMAuthorization if any.
     *
     * @param tblMauthorization The details of the TblMAuthorization to be updated; value cannot be null.
     * @return The updated TblMAuthorization.
     * @throws EntityNotFoundException if no TblMAuthorization is found with given input.
     */
    TblMAuthorization update(@Valid TblMAuthorization tblMauthorization);


    /**
     * Partially updates the details of an existing TblMAuthorization. It updates only the
     * fields of the existing TblMAuthorization which are passed in the tblMauthorizationPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblMAuthorization if any.
     *
     * @param tblmauthorizationId The id of the TblMAuthorization to be deleted; value cannot be null.
     * @param tblMauthorizationPatch The partial data of TblMAuthorization which is supposed to be updated; value cannot be null.
     * @return The updated TblMAuthorization.
     * @throws EntityNotFoundException if no TblMAuthorization is found with given input.
     */
    TblMAuthorization partialUpdate(Integer tblmauthorizationId, Map<String, Object> tblMauthorizationPatch);

    /**
     * Deletes an existing TblMAuthorization with the given id.
     *
     * @param tblmauthorizationId The id of the TblMAuthorization to be deleted; value cannot be null.
     * @return The deleted TblMAuthorization.
     * @throws EntityNotFoundException if no TblMAuthorization found with the given id.
     */
    TblMAuthorization delete(Integer tblmauthorizationId);

    /**
     * Deletes an existing TblMAuthorization with the given object.
     *
     * @param tblMauthorization The instance of the TblMAuthorization to be deleted; value cannot be null.
     */
    void delete(TblMAuthorization tblMauthorization);

    /**
     * Find all TblMAuthorizations matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblMAuthorizations.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TblMAuthorization> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TblMAuthorizations matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblMAuthorizations.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblMAuthorization> findAll(String query, Pageable pageable);

    /**
     * Exports all TblMAuthorizations matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all TblMAuthorizations matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the TblMAuthorizations in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TblMAuthorization.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);

    /*
     * Returns the associated relDelAuths for given TblMAuthorization id.
     *
     * @param authId value of authId; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated RelDelAuth instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<RelDelAuth> findAssociatedRelDelAuths(Integer authId, Pageable pageable);

    /*
     * Returns the associated relRoleAuths for given TblMAuthorization id.
     *
     * @param authId value of authId; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated RelRoleAuth instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<RelRoleAuth> findAssociatedRelRoleAuths(Integer authId, Pageable pageable);

}