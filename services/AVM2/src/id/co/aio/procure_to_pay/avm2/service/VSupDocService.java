/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.service;

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

import id.co.aio.procure_to_pay.avm2.VSupDoc;

/**
 * Service object for domain model class {@link VSupDoc}.
 */
public interface VSupDocService {

    /**
     * Creates a new VSupDoc. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VSupDoc if any.
     *
     * @param vsupDoc Details of the VSupDoc to be created; value cannot be null.
     * @return The newly created VSupDoc.
     */
    VSupDoc create(@Valid VSupDoc vsupDoc);


	/**
     * Returns VSupDoc by given id if exists.
     *
     * @param vsupdocId The id of the VSupDoc to get; value cannot be null.
     * @return VSupDoc associated with the given vsupdocId.
	 * @throws EntityNotFoundException If no VSupDoc is found.
     */
    VSupDoc getById(Integer vsupdocId);

    /**
     * Find and return the VSupDoc by given id if exists, returns null otherwise.
     *
     * @param vsupdocId The id of the VSupDoc to get; value cannot be null.
     * @return VSupDoc associated with the given vsupdocId.
     */
    VSupDoc findById(Integer vsupdocId);

	/**
     * Find and return the list of VSupDocs by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param vsupdocIds The id's of the VSupDoc to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return VSupDocs associated with the given vsupdocIds.
     */
    List<VSupDoc> findByMultipleIds(List<Integer> vsupdocIds, boolean orderedReturn);


    /**
     * Updates the details of an existing VSupDoc. It replaces all fields of the existing VSupDoc with the given vsupDoc.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VSupDoc if any.
     *
     * @param vsupDoc The details of the VSupDoc to be updated; value cannot be null.
     * @return The updated VSupDoc.
     * @throws EntityNotFoundException if no VSupDoc is found with given input.
     */
    VSupDoc update(@Valid VSupDoc vsupDoc);


    /**
     * Partially updates the details of an existing VSupDoc. It updates only the
     * fields of the existing VSupDoc which are passed in the vsupDocPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VSupDoc if any.
     *
     * @param vsupdocId The id of the VSupDoc to be deleted; value cannot be null.
     * @param vsupDocPatch The partial data of VSupDoc which is supposed to be updated; value cannot be null.
     * @return The updated VSupDoc.
     * @throws EntityNotFoundException if no VSupDoc is found with given input.
     */
    VSupDoc partialUpdate(Integer vsupdocId, Map<String, Object> vsupDocPatch);

    /**
     * Deletes an existing VSupDoc with the given id.
     *
     * @param vsupdocId The id of the VSupDoc to be deleted; value cannot be null.
     * @return The deleted VSupDoc.
     * @throws EntityNotFoundException if no VSupDoc found with the given id.
     */
    VSupDoc delete(Integer vsupdocId);

    /**
     * Deletes an existing VSupDoc with the given object.
     *
     * @param vsupDoc The instance of the VSupDoc to be deleted; value cannot be null.
     */
    void delete(VSupDoc vsupDoc);

    /**
     * Find all VSupDocs matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VSupDocs.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<VSupDoc> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all VSupDocs matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VSupDocs.
     *
     * @see Pageable
     * @see Page
     */
    Page<VSupDoc> findAll(String query, Pageable pageable);

    /**
     * Exports all VSupDocs matching the given input query to the given exportType format.
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
     * Exports all VSupDocs matching the given input query to the given exportType format.
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
     * Retrieve the count of the VSupDocs in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the VSupDoc.
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