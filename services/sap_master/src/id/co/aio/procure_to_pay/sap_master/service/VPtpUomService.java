/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master.service;

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

import id.co.aio.procure_to_pay.sap_master.VPtpUom;
import id.co.aio.procure_to_pay.sap_master.VPtpUomId;

/**
 * Service object for domain model class {@link VPtpUom}.
 */
public interface VPtpUomService {

    /**
     * Creates a new VPtpUom. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPtpUom if any.
     *
     * @param vptpUom Details of the VPtpUom to be created; value cannot be null.
     * @return The newly created VPtpUom.
     */
    VPtpUom create(@Valid VPtpUom vptpUom);


	/**
     * Returns VPtpUom by given id if exists.
     *
     * @param vptpuomId The id of the VPtpUom to get; value cannot be null.
     * @return VPtpUom associated with the given vptpuomId.
	 * @throws EntityNotFoundException If no VPtpUom is found.
     */
    VPtpUom getById(VPtpUomId vptpuomId);

    /**
     * Find and return the VPtpUom by given id if exists, returns null otherwise.
     *
     * @param vptpuomId The id of the VPtpUom to get; value cannot be null.
     * @return VPtpUom associated with the given vptpuomId.
     */
    VPtpUom findById(VPtpUomId vptpuomId);

	/**
     * Find and return the list of VPtpUoms by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param vptpuomIds The id's of the VPtpUom to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return VPtpUoms associated with the given vptpuomIds.
     */
    List<VPtpUom> findByMultipleIds(List<VPtpUomId> vptpuomIds, boolean orderedReturn);


    /**
     * Updates the details of an existing VPtpUom. It replaces all fields of the existing VPtpUom with the given vptpUom.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPtpUom if any.
     *
     * @param vptpUom The details of the VPtpUom to be updated; value cannot be null.
     * @return The updated VPtpUom.
     * @throws EntityNotFoundException if no VPtpUom is found with given input.
     */
    VPtpUom update(@Valid VPtpUom vptpUom);


    /**
     * Partially updates the details of an existing VPtpUom. It updates only the
     * fields of the existing VPtpUom which are passed in the vptpUomPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPtpUom if any.
     *
     * @param vptpuomId The id of the VPtpUom to be deleted; value cannot be null.
     * @param vptpUomPatch The partial data of VPtpUom which is supposed to be updated; value cannot be null.
     * @return The updated VPtpUom.
     * @throws EntityNotFoundException if no VPtpUom is found with given input.
     */
    VPtpUom partialUpdate(VPtpUomId vptpuomId, Map<String, Object> vptpUomPatch);

    /**
     * Deletes an existing VPtpUom with the given id.
     *
     * @param vptpuomId The id of the VPtpUom to be deleted; value cannot be null.
     * @return The deleted VPtpUom.
     * @throws EntityNotFoundException if no VPtpUom found with the given id.
     */
    VPtpUom delete(VPtpUomId vptpuomId);

    /**
     * Deletes an existing VPtpUom with the given object.
     *
     * @param vptpUom The instance of the VPtpUom to be deleted; value cannot be null.
     */
    void delete(VPtpUom vptpUom);

    /**
     * Find all VPtpUoms matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VPtpUoms.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<VPtpUom> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all VPtpUoms matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VPtpUoms.
     *
     * @see Pageable
     * @see Page
     */
    Page<VPtpUom> findAll(String query, Pageable pageable);

    /**
     * Exports all VPtpUoms matching the given input query to the given exportType format.
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
     * Exports all VPtpUoms matching the given input query to the given exportType format.
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
     * Retrieve the count of the VPtpUoms in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the VPtpUom.
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