/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_employee.service;

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

import id.co.aio.procure_to_pay.aio_employee.MstWorklocn;

/**
 * Service object for domain model class {@link MstWorklocn}.
 */
public interface MstWorklocnService {

    /**
     * Creates a new MstWorklocn. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on MstWorklocn if any.
     *
     * @param mstWorklocn Details of the MstWorklocn to be created; value cannot be null.
     * @return The newly created MstWorklocn.
     */
    MstWorklocn create(@Valid MstWorklocn mstWorklocn);


	/**
     * Returns MstWorklocn by given id if exists.
     *
     * @param mstworklocnId The id of the MstWorklocn to get; value cannot be null.
     * @return MstWorklocn associated with the given mstworklocnId.
	 * @throws EntityNotFoundException If no MstWorklocn is found.
     */
    MstWorklocn getById(String mstworklocnId);

    /**
     * Find and return the MstWorklocn by given id if exists, returns null otherwise.
     *
     * @param mstworklocnId The id of the MstWorklocn to get; value cannot be null.
     * @return MstWorklocn associated with the given mstworklocnId.
     */
    MstWorklocn findById(String mstworklocnId);

	/**
     * Find and return the list of MstWorklocns by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param mstworklocnIds The id's of the MstWorklocn to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return MstWorklocns associated with the given mstworklocnIds.
     */
    List<MstWorklocn> findByMultipleIds(List<String> mstworklocnIds, boolean orderedReturn);


    /**
     * Updates the details of an existing MstWorklocn. It replaces all fields of the existing MstWorklocn with the given mstWorklocn.
     *
     * This method overrides the input field values using Server side or database managed properties defined on MstWorklocn if any.
     *
     * @param mstWorklocn The details of the MstWorklocn to be updated; value cannot be null.
     * @return The updated MstWorklocn.
     * @throws EntityNotFoundException if no MstWorklocn is found with given input.
     */
    MstWorklocn update(@Valid MstWorklocn mstWorklocn);


    /**
     * Partially updates the details of an existing MstWorklocn. It updates only the
     * fields of the existing MstWorklocn which are passed in the mstWorklocnPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on MstWorklocn if any.
     *
     * @param mstworklocnId The id of the MstWorklocn to be deleted; value cannot be null.
     * @param mstWorklocnPatch The partial data of MstWorklocn which is supposed to be updated; value cannot be null.
     * @return The updated MstWorklocn.
     * @throws EntityNotFoundException if no MstWorklocn is found with given input.
     */
    MstWorklocn partialUpdate(String mstworklocnId, Map<String, Object> mstWorklocnPatch);

    /**
     * Deletes an existing MstWorklocn with the given id.
     *
     * @param mstworklocnId The id of the MstWorklocn to be deleted; value cannot be null.
     * @return The deleted MstWorklocn.
     * @throws EntityNotFoundException if no MstWorklocn found with the given id.
     */
    MstWorklocn delete(String mstworklocnId);

    /**
     * Deletes an existing MstWorklocn with the given object.
     *
     * @param mstWorklocn The instance of the MstWorklocn to be deleted; value cannot be null.
     */
    void delete(MstWorklocn mstWorklocn);

    /**
     * Find all MstWorklocns matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching MstWorklocns.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<MstWorklocn> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all MstWorklocns matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching MstWorklocns.
     *
     * @see Pageable
     * @see Page
     */
    Page<MstWorklocn> findAll(String query, Pageable pageable);

    /**
     * Exports all MstWorklocns matching the given input query to the given exportType format.
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
     * Exports all MstWorklocns matching the given input query to the given exportType format.
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
     * Retrieve the count of the MstWorklocns in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the MstWorklocn.
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


}